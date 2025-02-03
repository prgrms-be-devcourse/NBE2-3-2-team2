package com.example.letmovie.domain.reservation.repository;

import com.example.letmovie.domain.movie.entity.Showtime;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowtimeQueryRepository {
    List<Showtime> findShowtimesByMovieNameAndShowtimeDateAndTheaterName(String theaterName, String movieName, LocalDate showtimeDate, boolean isToday);

}
