package com.example.letmovie.domain.reservation.repository;

import com.example.letmovie.domain.movie.entity.Showtime;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.letmovie.domain.movie.entity.QShowtime.showtime;
import static com.example.letmovie.domain.movie.entity.QTheater.theater;
import static com.example.letmovie.domain.reservation.entity.QScreen.screen;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ShowtimeQueryRepositoryImpl implements ShowtimeQueryRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Showtime> findShowtimesByMovieNameAndShowtimeDateAndTheaterName(String theaterName, String movieName, LocalDate showtimeDate, boolean isToday) {
        log.info("----쿼리실행----");
        return queryFactory
                .selectFrom(showtime)
                .join(showtime.screen, screen).fetchJoin()
                .join(screen.theater, theater).fetchJoin()
                .where(
                        theater.theaterName.eq(theaterName),
                        showtime.movie.movieName.eq(movieName),
                        showtime.showtimeDate.eq(showtimeDate),
                        isToday ? showtime.showtimeTime.gt(LocalTime.now()) : null
                )
                .orderBy(showtime.showtimeTime.asc())
                .fetch();
    }
}
