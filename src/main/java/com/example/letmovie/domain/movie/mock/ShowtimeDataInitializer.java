package com.example.letmovie.domain.movie.mock;

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
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Component
public class ShowtimeDataInitializer implements CommandLineRunner {

    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final MovieJpaRepository movieJpaRepository;

    public ShowtimeDataInitializer(TheaterRepository theaterRepository, ScreenRepository screenRepository, ShowtimeRepository showtimeRepository, SeatRepository seatRepository, MovieJpaRepository movieJpaRepository) {
        this.theaterRepository = theaterRepository;
        this.screenRepository = screenRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
        this.movieJpaRepository = movieJpaRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Theater theater1 = theaterRepository.save(new Theater(null, "강남 메가박스"));
        Theater theater2 = theaterRepository.save(new Theater(null, "신촌 CGV"));
        Theater theater3 = theaterRepository.save(new Theater(null, "용산 아이맥스"));

        // Screen 데이터 추가
        Screen screen1 = screenRepository.save(new Screen(null, theater1, new ArrayList<>(), "1관", 200, 200));
        Screen screen2 = screenRepository.save(new Screen(null, theater1, new ArrayList<>(), "2관", 150, 150));
        Screen screen3 = screenRepository.save(new Screen(null, theater2, new ArrayList<>(), "3관", 180, 180));
        Screen screen4 = screenRepository.save(new Screen(null, theater3, new ArrayList<>(), "아이맥스관", 300, 300));

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

        // Showtime 데이터 추가
        //12 23
        showtimeRepository.save(new Showtime(null, screen1, movie1, LocalDate.of(2024, 12, 23), LocalTime.of(23, 30)));
        showtimeRepository.save(new Showtime(null, screen1, movie2, LocalDate.of(2024, 12, 23), LocalTime.of(8, 30)));
        showtimeRepository.save(new Showtime(null, screen1, movie3, LocalDate.of(2024, 12, 23), LocalTime.of(11, 0)));
        showtimeRepository.save(new Showtime(null, screen1, movie4, LocalDate.of(2024, 12, 23), LocalTime.of(13, 0)));

        //12 24
        showtimeRepository.save(new Showtime(null, screen2, movie8, LocalDate.of(2024, 12, 24), LocalTime.of(12, 0)));
        showtimeRepository.save(new Showtime(null, screen2, movie8, LocalDate.of(2024, 12, 24), LocalTime.of(14, 0)));
        showtimeRepository.save(new Showtime(null, screen3, movie8, LocalDate.of(2024, 12, 24), LocalTime.of(12, 0)));
        showtimeRepository.save(new Showtime(null, screen3, movie8, LocalDate.of(2024, 12, 24), LocalTime.of(14, 0)));
        showtimeRepository.save(new Showtime(null, screen4, movie9, LocalDate.of(2024, 12, 24), LocalTime.of(12, 0)));
        showtimeRepository.save(new Showtime(null, screen4, movie9, LocalDate.of(2024, 12, 24), LocalTime.of(14, 0)));

        //12 25
        showtimeRepository.save(new Showtime(null, screen1, movie5, LocalDate.of(2024, 12, 25), LocalTime.of(12, 30)));
        showtimeRepository.save(new Showtime(null, screen4, movie5, LocalDate.of(2024, 12, 25), LocalTime.of(14, 50)));



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
                int price = (row > 5) ? 20000 : 10000; // VIP와 REGULAR 가격 분리
                SeatType seatType = (row > 5) ? SeatType.VIP : SeatType.REGULAR;
                Seat seat = new Seat(null, screen, new ArrayList<>(), seatType, row, col, true, price);
                seatRepository.save(seat);
            }
        }
    }
}
