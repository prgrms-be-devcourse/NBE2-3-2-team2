package com.example.letmovie.domain.movie.mock;

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
import com.example.letmovie.domain.reservation.repository.ScreenRepository;
import com.example.letmovie.domain.reservation.repository.SeatRepository;
import com.example.letmovie.domain.reservation.repository.ShowtimeRepository;
import com.example.letmovie.domain.reservation.repository.TheaterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Component
@Profile("!test")
public class ShowtimeDataInitializer implements CommandLineRunner {

    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final MovieJpaRepository movieJpaRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public ShowtimeDataInitializer(TheaterRepository theaterRepository, ScreenRepository screenRepository, ShowtimeRepository showtimeRepository, SeatRepository seatRepository, MovieJpaRepository movieJpaRepository, MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.theaterRepository = theaterRepository;
        this.screenRepository = screenRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
        this.movieJpaRepository = movieJpaRepository;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        Member member = Member.builder()
                .nickname("홍길동")
                .email("jinyoung@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .birthDate("19881213")
                .authority(Authority.USER) // 기본값이 USER라면 생략 가능
                .grade(Grade.GENERAL)          // 기본값이 GENERAL이라면 생략 가능
                .memberStatus(MemberStatus.AVAILABLE) // 기본값이 AVAILABLE이라면 생략 가능
                .build();

        Member admin = Member.builder()
                .nickname("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .birthDate("19901213")
                .authority(Authority.ADMIN)  // 관리자
                .grade(Grade.GENERAL)
                .memberStatus(MemberStatus.AVAILABLE)
                .build();

        Member test = Member.builder()
                .nickname("김영희")
                .email("user2@example.com")
                .password(passwordEncoder.encode("1212"))
                .birthDate("19901223")
                .authority(Authority.USER) // 기본값이 USER라면 생략 가능
                .grade(Grade.GENERAL)          // 기본값이 GENERAL이라면 생략 가능
                .memberStatus(MemberStatus.AVAILABLE) // 기본값이 AVAILABLE이라면 생략 가능
                .build();

        // 생성한 Member를 저장
        memberRepository.save(member);
        memberRepository.save(admin);
        memberRepository.save(test);

        Theater theater1 = theaterRepository.save(Theater.builder().id(null).theaterName("강남 메가박스").build());
        Theater theater2 = theaterRepository.save( Theater.builder().id(null).theaterName("신촌 CGV").build());
        Theater theater3 = theaterRepository.save(Theater.builder().id(null).theaterName("용산 아이맥스").build());

        // Screen 데이터 추가
        Screen screen1 = screenRepository.save(Screen.builder().theater(theater1).seats(new ArrayList<>()).screenName("1관").build());
        Screen screen2 = screenRepository.save(Screen.builder().theater(theater1).seats(new ArrayList<>()).screenName("2관").build());
        Screen screen3 = screenRepository.save(Screen.builder().theater(theater2).seats(new ArrayList<>()).screenName("3관").build());
        Screen screen4 = screenRepository.save(Screen.builder().theater(theater3).seats(new ArrayList<>()).screenName("아이맥스관").build());




        // 2) 5만 건 Movie 데이터 추가 (제목이 전부 다르고, 한글로 시작)
        for (int i = 1; i <= 100000; i++) {
            // 1) 한글 "고유 문자열" 얻기 (가~힣 조합)
            String prefix = toHangulBase(i);

            // 2) 최종 영화 제목 예: "가 영화 1", "각 영화 2", ...
            String title = prefix + "-영화 코드네임 : " + i;

            // 3) 영화 코드 (M00001 ~ M50000)
            String code = "M" + String.format("%05d", i);

            // 4) Movie 엔티티 생성
            Movie dummyMovie = new Movie(
                    null,
                    title,              // 예) "가 영화 1"
                    code,               // 예) "M00001"
                    "감독 " + i,         // 임의
                    "15세이상관람가",    // 임의
                    "120",              // 임의(문자열)
                    "20250101",         // 임의(개봉일)
                    "장르",              // 임의(장르)
                    "제작사 " + i,       // 임의
                    Status.SHOW,        // 상영 상태
                    "https://www.themoviedb.org/t/p/w1280/rajTvnpDKRupZPpKJRxeJMKrIs6.jpg" + i,  // 임의
                    "still1.jpg" + i,   // 임의
                    "줄거리 진짜진짜 재밌고요" + i,       // 임의
                    "1234",                 // 임의
                    "1234"                  // 임의
            );

            // 5) DB에 저장
            movieJpaRepository.save(dummyMovie);
        }

        // 필요 시 flush / clear 처리





        Movie movie1 = movieJpaRepository.save(new Movie(null,
                "이처럼 사소한 것들", "M0001", "감독 1", "15세이상관람가", "120",
                "20240510", "액션", "제작사 1",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));
        Movie movie2 = movieJpaRepository.save(new Movie(null,
                "베놈", "M0002", "감독 2", "19세이상관람가", "130",
                "20240620", "드라마", "제작사 2",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/rajTvnpDKRupZPpKJRxeJMKrIs6.jpg", "still2.jpg", "줄거리 2", "150,000", "12340"));
        Movie movie3 =movieJpaRepository.save(new Movie(null,
                "레드원", "M0003", "감독 3", "12세이상관람가", "124",
                "20240710", "SF", "제작사 3",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/4zNUNhVpSqFggxqvdSXDRzy1QwE.jpg", "still1.jpg", "줄거리 3", "200,000", "12340"));
        Movie movie4 = movieJpaRepository.save(new Movie(null,
                "캐리온", "M0004", "감독 4", "전체관람가", "112",
                "20240810", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/moQ4z3yKFwd7CuNqrLINMl1pdp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        Movie movie5 = movieJpaRepository.save(new Movie(null,
                "슈퍼맨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240810", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/ykqOm6QiL2ergUSmPk2VseSTSzp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        Movie movie6 = movieJpaRepository.save(new Movie(null,
                "무파사: 라이온 킹", "M0004", "감독 4", "전체관람가", "112",
                "20240810", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/1VUExee8iFohFTwYVi4IOArYyaM.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        Movie movie7 = movieJpaRepository.save(new Movie(null,
                "시크릿 레벨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        Movie movie8 = movieJpaRepository.save(new Movie(null,
                "아노라", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        Movie movie9 = movieJpaRepository.save(new Movie(null,
                "헤러틱", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));

        // 상영 예정 영화
        movieJpaRepository.save(new Movie(null,
                "이처럼 사소한 것들", "M0001", "감독 1", "15세이상관람가", "120분",
                "2024-05-10", "액션", "제작사 1",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "베놈", "M0002", "감독 2", "12세이상관람가", "130",
                "2024-06-20", "드라마", "제작사 2",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/rajTvnpDKRupZPpKJRxeJMKrIs6.jpg", "still2.jpg", "줄거리 2", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "레드원", "M0003", "감독 3", "15세이상관람가", "124",
                "2024-07-10", "SF", "제작사 3",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/4zNUNhVpSqFggxqvdSXDRzy1QwE.jpg", "still1.jpg", "줄거리 3", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "캐리온", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/moQ4z3yKFwd7CuNqrLINMl1pdp.jpg", "still1.jpg", "줄거리 4", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "슈퍼맨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/ykqOm6QiL2ergUSmPk2VseSTSzp.jpg", "still1.jpg", "줄거리 4", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "무파사: 라이온 킹", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/1VUExee8iFohFTwYVi4IOArYyaM.jpg", "still1.jpg", "줄거리 4", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "시크릿 레벨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "still1.jpg", "줄거리 4", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "아노라", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "still1.jpg", "줄거리 4", "0", "0"));
        movieJpaRepository.save(new Movie(null,
                "헤러틱", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.PREV, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "still1.jpg", "줄거리 4", "0", "0"));

        // 추천 영화
        movieJpaRepository.save(new Movie(null,
                "듄:프로퍼시", "M0004", "감독 4", "15세이상관람가", "112",
                "20241210", "코미디", "제작사 4",
                Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/syQuoIyMmSrb7OmCub8y6RERhKf.jpg", "https://image.tmdb.org/t/p/original/lBoHzOgft2QfpjkVVvZCqeM4ttT.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "헤러틱", "M0004", "감독 4", "15세이상관람가", "112",
                "20241210", "코미디", "제작사 4",
                Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "https://image.tmdb.org/t/p/original/ag66gJCiZ06q1GSJuQlhGLi3Udx.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "극장판 짱구는 못말려: 우리들의 공룡일기", "M0004", "감독 4", "15세이상관람가", "112",
                "20241222", "코미디", "제작사 4",
                Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/sxwOUajjGzkPYrrXy2sdMEfeZpE.jpg", "https://image.tmdb.org/t/p/original/vW7lwVHkRePHzayZfoKOyYBeZqO.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "시크릿 레벨", "M0004", "감독 4", "15세이상관람가", "112",
                "20241223", "코미디", "제작사 4",
                Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "https://image.tmdb.org/t/p/original/ay8uvMrQNQcChIDMyfw60eAziQv.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "아노라", "M0004", "감독 4", "15세이상관람가", "112",
                "20241223", "코미디", "제작사 4",
                Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "https://image.tmdb.org/t/p/original/4cp40IyTpFfsT2IKpl0YlUkMBIR.jpg", "줄거리 4", "300,000", "12340"));

        // 페이징 확인 추가 데이터
        movieJpaRepository.save(new Movie(null,
                "이처럼 사소한 것들", "M0001", "감독 1", "15세이상관람가", "120",
                "20240510", "액션", "제작사 1",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "베놈", "M0002", "감독 2", "19세이상관람가", "130",
                "20240620", "드라마", "제작사 2",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/rajTvnpDKRupZPpKJRxeJMKrIs6.jpg", "still2.jpg", "줄거리 2", "150,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "레드원", "M0003", "감독 3", "12세이상관람가", "124",
                "20240710", "SF", "제작사 3",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/4zNUNhVpSqFggxqvdSXDRzy1QwE.jpg", "still1.jpg", "줄거리 3", "200,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "캐리온", "M0004", "감독 4", "전체관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/moQ4z3yKFwd7CuNqrLINMl1pdp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "슈퍼맨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/ykqOm6QiL2ergUSmPk2VseSTSzp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "무파사: 라이온 킹", "M0004", "감독 4", "전체관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/1VUExee8iFohFTwYVi4IOArYyaM.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "시크릿 레벨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "아노라", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "헤러틱", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "이처럼 사소한 것들", "M0001", "감독 1", "15세이상관람가", "120",
                "20240510", "액션", "제작사 1",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "베놈", "M0002", "감독 2", "19세이상관람가", "130",
                "20240620", "드라마", "제작사 2",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/rajTvnpDKRupZPpKJRxeJMKrIs6.jpg", "still2.jpg", "줄거리 2", "150,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "레드원", "M0003", "감독 3", "12세이상관람가", "124",
                "20240710", "SF", "제작사 3",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/4zNUNhVpSqFggxqvdSXDRzy1QwE.jpg", "still1.jpg", "줄거리 3", "200,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "캐리온", "M0004", "감독 4", "전체관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/moQ4z3yKFwd7CuNqrLINMl1pdp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "슈퍼맨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/ykqOm6QiL2ergUSmPk2VseSTSzp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "무파사: 라이온 킹", "M0004", "감독 4", "전체관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/1VUExee8iFohFTwYVi4IOArYyaM.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "시크릿 레벨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "아노라", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "헤러틱", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "이처럼 사소한 것들", "M0001", "감독 1", "15세이상관람가", "120",
                "20240510", "액션", "제작사 1",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "베놈", "M0002", "감독 2", "19세이상관람가", "130",
                "20240620", "드라마", "제작사 2",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/rajTvnpDKRupZPpKJRxeJMKrIs6.jpg", "still2.jpg", "줄거리 2", "150,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "레드원", "M0003", "감독 3", "12세이상관람가", "124",
                "20240710", "SF", "제작사 3",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/4zNUNhVpSqFggxqvdSXDRzy1QwE.jpg", "still1.jpg", "줄거리 3", "200,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "캐리온", "M0004", "감독 4", "전체관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/moQ4z3yKFwd7CuNqrLINMl1pdp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "슈퍼맨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/ykqOm6QiL2ergUSmPk2VseSTSzp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "무파사: 라이온 킹", "M0004", "감독 4", "전체관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/1VUExee8iFohFTwYVi4IOArYyaM.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "시크릿 레벨", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "아노라", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "헤러틱", "M0004", "감독 4", "15세이상관람가", "112",
                "20240901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
        movieJpaRepository.save(new Movie(null,
                "하루", "M0004", "감독 4", "15세이상관람가", "112",
                "20170901", "코미디", "제작사 4",
                Status.SHOW, "https://www.themoviedb.org/t/24.jpg", "still1.jpg", "줄거리", "300,000", "12340"));

// Showtime 데이터 추가
        showtimeRepository.save(Showtime.builder()
                .screen(screen1)
                .movie(movie1)
                .showtimeDate(LocalDate.of(2025, 1, 28))
                .showtimeTime(LocalTime.of(23, 30))
                .totalSeats(100)
                .remainingSeats(100)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen1)
                .movie(movie2)
                .showtimeDate(LocalDate.of(2025, 1, 26))
                .showtimeTime(LocalTime.of(8, 30))
                .totalSeats(100)
                .remainingSeats(100)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen1)
                .movie(movie3)
                .showtimeDate(LocalDate.of(2025, 1, 31))
                .showtimeTime(LocalTime.of(11, 0))
                .totalSeats(100)
                .remainingSeats(100)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen1)
                .movie(movie4)
                .showtimeDate(LocalDate.of(2025, 1, 30))
                .showtimeTime(LocalTime.of(13, 0))
                .totalSeats(100)
                .remainingSeats(100)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen2)
                .movie(movie8)
                .showtimeDate(LocalDate.of(2025, 1, 29))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(32)
                .remainingSeats(32)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen2)
                .movie(movie8)
                .showtimeDate(LocalDate.of(2025, 1, 28))
                .showtimeTime(LocalTime.of(14, 0))
                .totalSeats(32)
                .remainingSeats(32)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen3)
                .movie(movie8)
                .showtimeDate(LocalDate.of(2025, 1, 24))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(20)
                .remainingSeats(20)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen3)
                .movie(movie8)
                .showtimeDate(LocalDate.of(2025, 1, 27))
                .showtimeTime(LocalTime.of(14, 0))
                .totalSeats(20)
                .remainingSeats(20)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen4)
                .movie(movie9)
                .showtimeDate(LocalDate.of(2025, 1, 26))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(25)
                .remainingSeats(25)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen4)
                .movie(movie9)
                .showtimeDate(LocalDate.of(2024, 1, 25))
                .showtimeTime(LocalTime.of(14, 0))
                .totalSeats(25)
                .remainingSeats(25)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen1)
                .movie(movie5)
                .showtimeDate(LocalDate.of(2025, 1, 26))
                .showtimeTime(LocalTime.of(12, 30))
                .totalSeats(100)
                .remainingSeats(100)
                .build());

        showtimeRepository.save(Showtime.builder()
                .screen(screen4)
                .movie(movie5)
                .showtimeDate(LocalDate.of(2025, 1, 26))
                .showtimeTime(LocalTime.of(14, 50))
                .totalSeats(25)
                .remainingSeats(25)
                .build());


        // Seat 데이터 추가
        addSeatsForScreen(screen1, 10, 10);
        addSeatsForScreen(screen2, 8, 4);
        addSeatsForScreen(screen3, 5, 4);
        addSeatsForScreen(screen4, 5, 5);
    }

    private void addSeatsForScreen(Screen screen, int rows, int cols) {
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= cols; col++) {
                boolean isAble = !(row == 6 && col == 2); // 특정 좌석 비활성화
                int price = (row > 9) ? 20000 : 10000; // VIP와 REGULAR 가격 분리
                SeatType seatType = (row > 9) ? SeatType.VIP : SeatType.REGULAR; // VIP와 REGULAR 가격 분리
                Seat seat = Seat.builder()
                        .screen(screen)
                        .seatType(seatType)
                        .seatLow(row)
                        .seatCol(col)
                        .price(price).build();
                seatRepository.save(seat);
            }
        }
    }

    /**
     * 숫자를 한글(가~힣) 1글자 이상으로 변환하는 "Base 11172" 함수
     */
    private String toHangulBase(int num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            num--;
            int remainder = num % 11172;
            char c = (char) (0xAC00 + remainder);
            sb.insert(0, c);
            num /= 11172;
        }
        return sb.toString();
    }
}
