package com.example.letmovie.domain.reservation.service;

import com.example.letmovie.domain.movie.entity.Movie;
import com.example.letmovie.domain.movie.entity.Showtime;
import com.example.letmovie.domain.movie.entity.Status;
import com.example.letmovie.domain.movie.entity.Theater;
import com.example.letmovie.domain.movie.repository.MovieJpaRepository;
import com.example.letmovie.domain.reservation.dto.response.MovieNamesResponseDTO;
import com.example.letmovie.domain.reservation.dto.response.ShowTimeResponseDTO;
import com.example.letmovie.domain.reservation.dto.response.TheaterResponseDTO;
import com.example.letmovie.domain.reservation.entity.Screen;
import com.example.letmovie.domain.reservation.repository.ScreenRepository;
import com.example.letmovie.domain.reservation.repository.ShowtimeRepository;
import com.example.letmovie.domain.reservation.repository.TheaterRepository;
import com.example.letmovie.domain.reservation.service.ShowtimeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ShowtimeServiceTest {

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieJpaRepository movieJpaRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private MovieJpaRepository movieRepository;


    @AfterEach
    void clean(){
        showtimeRepository.deleteAll();
        screenRepository.deleteAll();
        theaterRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    @DisplayName("날짜로 영화 이름 가져오기.")
    void test1(){
        //given
        Theater theater = theaterRepository.save(Theater.builder()
                .theaterName("제주 cgv")
                .build());

        Screen screen = screenRepository.save(Screen.builder()
                .theater(theater)
                .seats(new ArrayList<>())
                .screenName("1관")
                .build());

        Movie movie = movieJpaRepository.save(new Movie(null,
                "오징어게임2", "M0001", "감독 1", "15세", "120분",
                "2024-11-11", "액션", "제작사 1",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));

        showtimeRepository.save(Showtime.builder()
                .screen(screen)
                .movie(movie)
                .showtimeDate(LocalDate.parse("2024-12-28"))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(200)
                .remainingSeats(200)
                .build());

        //then
        MovieNamesResponseDTO dto = showtimeService.findMovieNameByDate("2024-12-28");
        List<String> movieNames = dto.getMovieNames();

        assertEquals(movieNames.get(0), "오징어게임2");
    }

    @Test
    @DisplayName("영화 이름, 날짜로 극장 가져오기.")
    void test2(){
        //given
        Theater theater = theaterRepository.save(Theater.builder()
                .theaterName("제주 cgv")
                .build());

        Screen screen = screenRepository.save(Screen.builder()
                .theater(theater)
                .seats(new ArrayList<>())
                .screenName("1관")
                .build());

        Movie movie = movieJpaRepository.save(new Movie(null,
                "오징어게임2", "M0001", "감독 1", "15세", "120분",
                "2024-11-11", "액션", "제작사 1",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));

        showtimeRepository.save(Showtime.builder()
                .screen(screen)
                .movie(movie)
                .showtimeDate(LocalDate.parse("2024-12-28"))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(200)
                .remainingSeats(200)
                .build());

        //when
        List<TheaterResponseDTO> dto = showtimeService.findTheatersByMovieNameAndDate("오징어게임2", "2024-12-28");

        //then
        assertEquals(1L, showtimeRepository.count());
        assertEquals("제주 cgv", dto.get(0).getName());
    }

    @Test
    @DisplayName("영화 날짜, 영화, 극장 선택으로 상영관,시간대 찾기.")
    void test3(){
        //given
        Theater theater = theaterRepository.save(Theater.builder()
                .theaterName("제주 cgv")
                .build());

        Screen screen = screenRepository.save(Screen.builder()
                .theater(theater)
                .seats(new ArrayList<>())
                .screenName("1관")
                .build());

        Movie movie = movieJpaRepository.save(new Movie(null,
                "오징어게임2", "M0001", "감독 1", "15세", "120분",
                "2024-11-11", "액션", "제작사 1",
                Status.SHOW, "https://www.themoviedb.org/t/p/w1280/iIdBv0pMqZ9XKYOQeK42N1LZIeN.jpg", "still1.jpg", "줄거리 1", "100,000", "12340"));

        showtimeRepository.save(Showtime.builder()
                .screen(screen)
                .movie(movie)
                .showtimeDate(LocalDate.parse("2024-12-28"))
                .showtimeTime(LocalTime.of(12, 0))
                .totalSeats(200)
                .remainingSeats(200)
                .build());

        //when
        List<ShowTimeResponseDTO> dto = showtimeService.findShowtimeByDateAndMovieNameAndTheater("오징어게임2", "2024-12-28", "제주 cgv");

        //then
        assertEquals(1, showtimeRepository.count());
        assertEquals("제주 cgv", dto.get(0).getTheaterName());
        assertEquals("1관", dto.get(0).getScreenName());
        assertEquals(200, dto.get(0).getScreenTotalSeat());
        assertEquals(200, dto.get(0).getScreenRemainSeat());
        assertEquals("12:00", dto.get(0).getShowtime());
    }
}
