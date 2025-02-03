package com.example.letmovie.domain.reservation.service;

import com.example.letmovie.domain.member.entity.Authority;
import com.example.letmovie.domain.member.entity.Grade;
import com.example.letmovie.domain.member.entity.Member;
import com.example.letmovie.domain.member.entity.MemberStatus;
import com.example.letmovie.domain.member.repository.MemberRepository;
import com.example.letmovie.domain.movie.entity.Movie;
import com.example.letmovie.domain.movie.entity.Showtime;
import com.example.letmovie.domain.movie.entity.Status;
import com.example.letmovie.domain.movie.entity.Theater;
import com.example.letmovie.domain.movie.repository.MovieJpaRepository;
import com.example.letmovie.domain.reservation.entity.Screen;
import com.example.letmovie.domain.reservation.entity.Seat;
import com.example.letmovie.domain.reservation.entity.SeatType;
import com.example.letmovie.domain.reservation.facade.OptimisticLockReservationFacade;
import com.example.letmovie.domain.reservation.repository.*;
import com.example.letmovie.domain.reservation.service.lock.PessimisticLockReservationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class ReservationTimeServiceTest {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieJpaRepository movieJpaRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private PessimisticLockReservationService pessimisticLockReservationService;

    @Autowired
    private OptimisticLockReservationFacade optimisticLockReservationService;

    @Value("${thread.pool.size:4}")
    private int threadPoolSize;

    @AfterEach
    void clean(){
        reservationRepository.deleteAll(); // reservation 먼저 삭제
        seatRepository.deleteAll();
        showtimeRepository.deleteAll();
        screenRepository.deleteAll();
        theaterRepository.deleteAll();
        movieJpaRepository.deleteAll();
        memberRepository.deleteAll();
    }


    @Test
    @DisplayName("동시성 이슈 비관적 락으로 해결 + 응답시간 측정")
    void test1_withResponseTime() throws InterruptedException {

        //given
        int threadCount = 5000;
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Theater theater = theaterRepository.save(Theater.builder()
                .theaterName("영국 롯데시네마")
                .build());

        Screen screen = screenRepository.save(Screen.builder()
                .theater(theater)
                .screenName("100관")
                .build());

        Movie movie = movieJpaRepository.save(
                new Movie(null, "오징어게임2", "M0001", "봉준호", "19세", "1200분",
                        "2024-11-11", "액션", "쇼박스",
                        Status.SHOW, "url1", "still1.jpg", "줄거리 1", "100,000", "12340"));

        int rows = 10;
        int cols = 10;
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= cols; col++) {
                boolean isAble = !(row == 1 && col == 1);
                int price = (row > 9) ? 20000 : 10000;
                SeatType seatType = (row > 9) ? SeatType.VIP : SeatType.REGULAR;
                Seat seat = Seat.builder()
                        .screen(screen)
                        .seatType(seatType)
                        .seatLow(row)
                        .seatCol(col)
                        .price(price)
                        .build();
                seatRepository.save(seat);
            }
        }

        Showtime showTime = showtimeRepository.save(Showtime.builder()
                .screen(screen)
                .movie(movie)
                .showtimeDate(LocalDate.parse("2024-12-28"))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(rows * cols)
                .remainingSeats(rows * cols)
                .build());

        Member member = Member.builder()
                .nickname("홍길동")
                .email("jinyoung@gmail.com")
                .password("1234")
                .birthDate("881213")
                .authority(Authority.USER)
                .grade(Grade.GENERAL)
                .memberStatus(MemberStatus.AVAILABLE)
                .build();
        memberRepository.save(member);

        List<String> seats = new ArrayList<>();
        seats.add("1-1");

        // 응답 시간을 기록할 리스트 (스레드 안전성을 위해 동기화 리스트 또는 ConcurrentLinkedQueue 사용)
        List<Long> responseTimes = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        //when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                long startTime = System.nanoTime(); // 요청 시작 시간
                try {
                    pessimisticLockReservationService.reservation(seats, member.getId(), showTime.getId());
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    long endTime = System.nanoTime(); // 요청 종료 시간
                    long duration = endTime - startTime; // 나노초 단위
                    responseTimes.add(duration);
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        Showtime findShowTime = showtimeRepository.findById(showTime.getId())
                .orElseThrow();
        // 예: 100명이 동시에 1개 좌석을 예약하면, 보통 1명 성공, 나머지는 실패
        assertEquals(99, findShowTime.getRemainingSeats());

        //then
        log.info("성공 요청 수 : {}", successCount.get());
        log.info("실패 요청 수 : {}", failCount.get());

        // 응답 시간 통계 (ms로 변환)
        List<Long> copy = new ArrayList<>(responseTimes);
        double minMs = copy.stream().mapToLong(v -> v).min().orElse(0L) / 1_000_000.0;
        double maxMs = copy.stream().mapToLong(v -> v).max().orElse(0L) / 1_000_000.0;
        double avgMs = copy.stream().mapToLong(v -> v).average().orElse(0.0) / 1_000_000.0;

        log.info("최소 응답 시간 : {}", String.format("%.2f", minMs) + " ms");
        log.info("최대 응답 시간 : {}", String.format("%.2f", maxMs) + " ms");
        log.info("평균 응답 시간 : {}",String.format("%.2f", avgMs) + " ms");
    }


    @Test
    @DisplayName("동시성 이슈 낙관적 락으로 해결 + 응답시간 측정")
    void test2_withResponseTime() throws InterruptedException {
        //given
        int threadCount = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Theater theater = theaterRepository.save(Theater.builder()
                .theaterName("영국 롯데시네마")
                .build());

        Screen screen = screenRepository.save(Screen.builder()
                .theater(theater)
                .screenName("100관")
                .build());

        Movie movie = movieJpaRepository.save(
                new Movie(null, "오징어게임2", "M0001", "봉준호", "19세", "1200분",
                        "2024-11-11", "액션", "쇼박스",
                        Status.SHOW, "url1", "still1.jpg", "줄거리 1", "100,000", "12340"));

        int rows = 10;
        int cols = 10;
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= cols; col++) {
                boolean isAble = !(row == 1 && col == 1);
                int price = (row > 9) ? 20000 : 10000;
                SeatType seatType = (row > 9) ? SeatType.VIP : SeatType.REGULAR;
                Seat seat = Seat.builder()
                        .screen(screen)
                        .seatType(seatType)
                        .seatLow(row)
                        .seatCol(col)
                        .price(price)
                        .build();
                seatRepository.save(seat);
            }
        }

        Showtime showTime = showtimeRepository.save(Showtime.builder()
                .screen(screen)
                .movie(movie)
                .showtimeDate(LocalDate.parse("2024-12-28"))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(rows * cols)
                .remainingSeats(rows * cols)
                .build());

        Member member = Member.builder()
                .nickname("홍길동")
                .email("jinyoung@gmail.com")
                .password("1234")
                .birthDate("881213")
                .authority(Authority.USER)
                .grade(Grade.GENERAL)
                .memberStatus(MemberStatus.AVAILABLE)
                .build();
        memberRepository.save(member);

        List<String> seats = new ArrayList<>();
        seats.add("1-1");

        // 응답 시간을 기록할 리스트 (스레드 안전성을 위해 동기화 리스트 또는 ConcurrentLinkedQueue 사용)
        List<Long> responseTimes = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        //when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                long startTime = System.nanoTime(); // 요청 시작 시간
                try {
                    optimisticLockReservationService.reservation(seats, member.getId(), showTime.getId());
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    long endTime = System.nanoTime(); // 요청 종료 시간
                    long duration = endTime - startTime; // 나노초 단위
                    responseTimes.add(duration);
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Showtime findShowTime = showtimeRepository.findById(showTime.getId())
                .orElseThrow();
        // 예: 100명이 동시에 1개 좌석을 예약하면, 보통 1명 성공, 나머지는 실패
        assertEquals(99, findShowTime.getRemainingSeats());

        //then
        log.info("성공 요청 수 : {}", successCount.get());
        log.info("실패 요청 수 : {}", failCount.get());

        // 응답 시간 통계 (ms로 변환)
        List<Long> copy = new ArrayList<>(responseTimes);
        double minMs = copy.stream().mapToLong(v -> v).min().orElse(0L) / 1_000_000.0;
        double maxMs = copy.stream().mapToLong(v -> v).max().orElse(0L) / 1_000_000.0;
        double avgMs = copy.stream().mapToLong(v -> v).average().orElse(0.0) / 1_000_000.0;

        log.info("최소 응답 시간 : {}", String.format("%.2f", minMs) + " ms");
        log.info("최대 응답 시간 : {}", String.format("%.2f", maxMs) + " ms");
        log.info("평균 응답 시간 : {}",String.format("%.2f", avgMs) + " ms");
    }

    @Test
    @DisplayName("동시성 이슈 비관적 락 - 여러 좌석 vs 다수 유저")
    void test3_multiSeats() throws InterruptedException {
        // given
        int threadCount = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Theater theater = theaterRepository.save(Theater.builder()
                .theaterName("영국 롯데시네마")
                .build());

        Screen screen = screenRepository.save(Screen.builder()
                .theater(theater)
                .screenName("100관")
                .build());

        Movie movie = movieJpaRepository.save(new Movie(null,
                "오징어게임2", "M0001", "봉준호", "19세", "1200분",
                "2024-11-11", "액션", "쇼박스",
                Status.SHOW, "url.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));

        int rows = 10;
        int cols = 10;
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= cols; col++) {
                Seat seat = Seat.builder()
                        .screen(screen)
                        .seatType(SeatType.REGULAR) // 예시로 전부 REGULAR
                        .seatLow(row)
                        .seatCol(col)
                        .price(10000)
                        .build();
                seatRepository.save(seat);
            }
        }

        // 좌석이 10x10 = 100개
        Showtime showTime = showtimeRepository.save(Showtime.builder()
                .screen(screen)
                .movie(movie)
                .showtimeDate(LocalDate.parse("2024-12-28"))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(rows * cols)
                .remainingSeats(rows * cols)
                .build());

        Member member = Member.builder()
                .nickname("홍길동")
                .email("jinyoung@gmail.com")
                .password("1234")
                .birthDate("881213")
                .authority(Authority.USER)
                .grade(Grade.GENERAL)
                .memberStatus(MemberStatus.AVAILABLE)
                .build();
        memberRepository.save(member);

        //응답 시간이나 성공/실패 카운트를 기록
        List<Long> responseTimes = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                // 스레드마다 "랜덤 좌석"을 1개 선택
                // (row=1..10, col=1..10)
                Random rand = new Random();
                int randRow = rand.nextInt(rows) + 1;
                int randCol = rand.nextInt(cols) + 1;
                List<String> seats = new ArrayList<>();
                seats.add(randRow + "-" + randCol);

                long startTime = System.nanoTime();
                try {
                    // 비관적 락 예약 로직 호출
                    pessimisticLockReservationService.reservation(seats, member.getId(), showTime.getId());
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    long endTime = System.nanoTime();
                    responseTimes.add(endTime - startTime);
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Showtime findShowTime = showtimeRepository.findById(showTime.getId()).orElseThrow();

        // then
        // 1) 남은좌석 = 총좌석 - 실제로 성공한 예약 수
        //    (좌석 충돌 등으로 인해 일부만 성공했을 것)
        int actualSuccess = successCount.get();
        int expectedRemaining = (rows * cols) - actualSuccess;
        int dbRemainingSeats = findShowTime.getRemainingSeats();

        // 간단 검증
        System.out.println("성공 요청 수: " + actualSuccess);
        System.out.println("실패 요청 수: " + failCount.get());
        System.out.println("DB remainingSeats: " + dbRemainingSeats);

        List<Long> copyTimes = new ArrayList<>(responseTimes);
        double minMs = copyTimes.stream().mapToLong(v -> v).min().orElse(0) / 1_000_000.0;
        double maxMs = copyTimes.stream().mapToLong(v -> v).max().orElse(0) / 1_000_000.0;
        double avgMs = copyTimes.stream().mapToLong(v -> v).average().orElse(0) / 1_000_000.0;

        System.out.println("최소 응답 시간: " + String.format("%.2f", minMs) + " ms");
        System.out.println("최대 응답 시간: " + String.format("%.2f", maxMs) + " ms");
        System.out.println("평균 응답 시간: " + String.format("%.2f", avgMs) + " ms");

        assertEquals(expectedRemaining, dbRemainingSeats);
    }

    @Test
    @DisplayName("동시성 이슈 낙관적 락 - 여러 좌석 vs 다수 유저")
    void test4_multiSeats() throws InterruptedException {
        // given
        int threadCount = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Theater theater = theaterRepository.save(Theater.builder()
                .theaterName("영국 롯데시네마")
                .build());

        Screen screen = screenRepository.save(Screen.builder()
                .theater(theater)
                .screenName("100관")
                .build());

        Movie movie = movieJpaRepository.save(new Movie(null,
                "오징어게임2", "M0001", "봉준호", "19세", "1200분",
                "2024-11-11", "액션", "쇼박스",
                Status.SHOW, "url.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));

        int rows = 10;
        int cols = 10;
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= cols; col++) {
                Seat seat = Seat.builder()
                        .screen(screen)
                        .seatType(SeatType.REGULAR) // 예시로 전부 REGULAR
                        .seatLow(row)
                        .seatCol(col)
                        .price(10000)
                        .build();
                seatRepository.save(seat);
            }
        }

        // 좌석이 10x10 = 100개
        Showtime showTime = showtimeRepository.save(Showtime.builder()
                .screen(screen)
                .movie(movie)
                .showtimeDate(LocalDate.parse("2024-12-28"))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(rows * cols)
                .remainingSeats(rows * cols)
                .build());

        Member member = Member.builder()
                .nickname("홍길동")
                .email("jinyoung@gmail.com")
                .password("1234")
                .birthDate("881213")
                .authority(Authority.USER)
                .grade(Grade.GENERAL)
                .memberStatus(MemberStatus.AVAILABLE)
                .build();
        memberRepository.save(member);

        List<Long> responseTimes = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                // 스레드마다 "랜덤 좌석"을 1개 선택
                // (row=1..10, col=1..10)
                Random rand = new Random();
                int randRow = rand.nextInt(rows) + 1;
                int randCol = rand.nextInt(cols) + 1;
                List<String> seats = new ArrayList<>();
                seats.add(randRow + "-" + randCol);

                long startTime = System.nanoTime();
                try {
                    optimisticLockReservationService.reservation(seats, member.getId(), showTime.getId());
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    long endTime = System.nanoTime();
                    responseTimes.add(endTime - startTime);
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Showtime findShowTime = showtimeRepository.findById(showTime.getId()).orElseThrow();

        // then
        // 1) 남은좌석 = 총좌석 - 실제로 성공한 예약 수
        //    (좌석 충돌 등으로 인해 일부만 성공했을 것)
        int actualSuccess = successCount.get();
        int expectedRemaining = (rows * cols) - actualSuccess;
        int dbRemainingSeats = findShowTime.getRemainingSeats();

        // 간단 검증
        System.out.println("성공 요청 수: " + actualSuccess);
        System.out.println("실패 요청 수: " + failCount.get());
        System.out.println("DB remainingSeats: " + dbRemainingSeats);

        List<Long> copyTimes = new ArrayList<>(responseTimes);
        double minMs = copyTimes.stream().mapToLong(v -> v).min().orElse(0) / 1_000_000.0;
        double maxMs = copyTimes.stream().mapToLong(v -> v).max().orElse(0) / 1_000_000.0;
        double avgMs = copyTimes.stream().mapToLong(v -> v).average().orElse(0) / 1_000_000.0;

        System.out.println("최소 응답 시간: " + String.format("%.2f", minMs) + " ms");
        System.out.println("최대 응답 시간: " + String.format("%.2f", maxMs) + " ms");
        System.out.println("평균 응답 시간: " + String.format("%.2f", avgMs) + " ms");

        assertEquals(expectedRemaining, dbRemainingSeats);
    }
}
