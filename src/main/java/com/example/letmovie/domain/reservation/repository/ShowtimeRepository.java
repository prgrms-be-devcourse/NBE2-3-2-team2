package com.example.letmovie.domain.reservation.repository;

import com.example.letmovie.domain.movie.entity.Showtime;
import com.example.letmovie.domain.movie.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ShowtimeRepository  extends JpaRepository<Showtime, Long> {

    List<Showtime> findByShowtimeDate(LocalDate showtimeDate);

    @Query("SELECT DISTINCT t FROM Showtime s " +
            "JOIN s.screen sc " +
            "JOIN sc.theater t " +
            "WHERE s.movie.movieName = :movieName AND s.showtimeDate = :showtimeDate")
    List<Theater> findTheatersByMovieNameAndShowtimeDate(
            @Param("movieName") String movieName,
            @Param("showtimeDate") LocalDate showtimeDate);

    @Query("SELECT s FROM Showtime s " +
            "JOIN FETCH s.screen sc " +
            "JOIN FETCH sc.theater t " +
            "WHERE t.theaterName = :theaterName " +
            "AND s.movie.movieName = :movieName " +
            "AND s.showtimeDate = :showtimeDate " +
//            "AND s.showtimeTime > :currentTime " + // 현재 시간 이후 필터링 - 수정필요
            "ORDER BY s.showtimeTime ASC") // 상영 시작 시간으로 정렬
    List<Showtime> findShowtimesByMovieAndDateAndTheater(
            @Param("theaterName") String theaterName,
            @Param("movieName") String movieName,
            @Param("showtimeDate") LocalDate showtimeDate,
            @Param("currentTime") LocalTime currentTime
    );
}