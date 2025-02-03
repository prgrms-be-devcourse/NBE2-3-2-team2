package com.example.letmovie.domain.movie.mock;

import com.example.letmovie.domain.movie.entity.Movie;
import com.example.letmovie.domain.movie.entity.Status;
import com.example.letmovie.domain.movie.repository.MovieJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class MovieDataInitializer implements CommandLineRunner {

    private final MovieJpaRepository movieJpaRepository;

    public MovieDataInitializer(MovieJpaRepository movieJpaRepository) {
        this.movieJpaRepository = movieJpaRepository;
    }

    @Override
    public void run(String... args) {
        // 초기 데이터 추가
        if (movieJpaRepository.count() == 0) { // 중복 방지
            // 현재 상영 중인 영화
            movieJpaRepository.save(new Movie(null,
                    "이처럼 사소한 것들", "M0001", "감독 1", "15세", "120분",
                    "2024-05-10", "액션", "제작사 1",
                    Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));
            movieJpaRepository.save(new Movie(null,
                    "베놈", "M0002", "감독 2", "19세", "130분",
                    "2024-06-20", "드라마", "제작사 2",
                    Status.SHOW, "https://www.themoviedb.org/t/p/w1280/rajTvnpDKRupZPpKJRxeJMKrIs6.jpg", "still2.jpg", "줄거리 2", "150,000", "12340"));
            movieJpaRepository.save(new Movie(null,
                    "레드원", "M0003", "감독 3", "12세", "124분",
                    "2024-07-10", "SF", "제작사 3",
                    Status.SHOW, "https://www.themoviedb.org/t/p/w1280/4zNUNhVpSqFggxqvdSXDRzy1QwE.jpg", "still1.jpg", "줄거리 3", "200,000", "12340"));
            movieJpaRepository.save(new Movie(null,
                    "캐리온", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.SHOW, "https://www.themoviedb.org/t/p/w1280/moQ4z3yKFwd7CuNqrLINMl1pdp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
            movieJpaRepository.save(new Movie(null,
                    "슈퍼맨", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.SHOW, "https://www.themoviedb.org/t/p/w1280/ykqOm6QiL2ergUSmPk2VseSTSzp.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
            movieJpaRepository.save(new Movie(null,
                    "무파사: 라이온 킹", "M0004", "감독 4", "전체", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.SHOW, "https://www.themoviedb.org/t/p/w1280/1VUExee8iFohFTwYVi4IOArYyaM.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
            movieJpaRepository.save(new Movie(null,
                    "시크릿 레벨", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.SHOW, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
            movieJpaRepository.save(new Movie(null,
                    "아노라", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.SHOW, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));
            movieJpaRepository.save(new Movie(null,
                    "헤러틱", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.SHOW, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "still1.jpg", "줄거리 4", "300,000", "12340"));

            // 상영 예정 영화
            movieJpaRepository.save(new Movie(null,
                    "이처럼 사소한 것들", "M0001", "감독 1", "15세", "120분",
                    "2024-05-10", "액션", "제작사 1",
                    Status.PREV, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "0", "0"));
            movieJpaRepository.save(new Movie(null,
                    "베놈", "M0002", "감독 2", "12세", "130분",
                    "2024-06-20", "드라마", "제작사 2",
                    Status.PREV, "https://www.themoviedb.org/t/p/w1280/rajTvnpDKRupZPpKJRxeJMKrIs6.jpg", "still2.jpg", "줄거리 2", "0", "0"));
            movieJpaRepository.save(new Movie(null,
                    "레드원", "M0003", "감독 3", "15세", "124분",
                    "2024-07-10", "SF", "제작사 3",
                    Status.PREV, "https://www.themoviedb.org/t/p/w1280/4zNUNhVpSqFggxqvdSXDRzy1QwE.jpg", "still1.jpg", "줄거리 3", "0", "0"));
            movieJpaRepository.save(new Movie(null,
                    "캐리온", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.PREV, "https://www.themoviedb.org/t/p/w1280/moQ4z3yKFwd7CuNqrLINMl1pdp.jpg", "still1.jpg", "줄거리 4", "0", "0"));
            movieJpaRepository.save(new Movie(null,
                    "슈퍼맨", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.PREV, "https://www.themoviedb.org/t/p/w1280/ykqOm6QiL2ergUSmPk2VseSTSzp.jpg", "still1.jpg", "줄거리 4", "0", "0"));
            movieJpaRepository.save(new Movie(null,
                    "무파사: 라이온 킹", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.PREV, "https://www.themoviedb.org/t/p/w1280/1VUExee8iFohFTwYVi4IOArYyaM.jpg", "still1.jpg", "줄거리 4", "0", "0"));
            movieJpaRepository.save(new Movie(null,
                    "시크릿 레벨", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.PREV, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "still1.jpg", "줄거리 4", "0", "0"));
            movieJpaRepository.save(new Movie(null,
                    "아노라", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.PREV, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "still1.jpg", "줄거리 4", "0", "0"));
            movieJpaRepository.save(new Movie(null,
                    "헤러틱", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.PREV, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "still1.jpg", "줄거리 4", "0", "0"));

            // 추천 영화
            movieJpaRepository.save(new Movie(null,
                    "헤러틱", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/5HJqjCTcaE1TFwnNh3Dn21be2es.jpg", "https://image.tmdb.org/t/p/original/ag66gJCiZ06q1GSJuQlhGLi3Udx.jpg", "줄거리 4", "300,000", "12340"));
            movieJpaRepository.save(new Movie(null,
                    "극장판 짱구는 못말려: 우리들의 공룡일기", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/sxwOUajjGzkPYrrXy2sdMEfeZpE.jpg", "https://image.tmdb.org/t/p/original/vW7lwVHkRePHzayZfoKOyYBeZqO.jpg", "줄거리 4", "300,000", "12340"));
            movieJpaRepository.save(new Movie(null,
                    "시크릿 레벨", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/cSEnXTGHlncgl7x0A9OH4PDFsx6.jpg", "https://image.tmdb.org/t/p/original/ay8uvMrQNQcChIDMyfw60eAziQv.jpg", "줄거리 4", "300,000", "12340"));
            movieJpaRepository.save(new Movie(null,
                    "아노라", "M0004", "감독 4", "15세", "112분",
                    "2024-08-10", "코미디", "제작사 4",
                    Status.RECOMMEND, "https://www.themoviedb.org/t/p/w1280/mwguqSMRCA3NgpPoRsXdFhid25m.jpg", "https://image.tmdb.org/t/p/original/4cp40IyTpFfsT2IKpl0YlUkMBIR.jpg", "줄거리 4", "300,000", "12340"));

        }
    }
}