package com.example.letmovie.domain.admin.service;

import com.example.letmovie.domain.admin.repository.AdminMovieJpaRepository;
import com.example.letmovie.domain.admin.repository.AdminScreenRepository;
import com.example.letmovie.domain.admin.repository.AdminSeatRepository;
import com.example.letmovie.domain.admin.repository.AdminShowtimeRepository;
import com.example.letmovie.domain.movie.entity.Movie;
import com.example.letmovie.domain.movie.entity.Showtime;
import com.example.letmovie.domain.reservation.entity.Screen;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminShowtimeServiceImpl {
    @Autowired
    private AdminMovieJpaRepository adminMovieJpaRepository;

    @Autowired
    private AdminScreenRepository adminScreenRepository;

    @Autowired
    private AdminShowtimeRepository adminShowtimeRepository;

    @Autowired
    private AdminSeatRepository adminSeatRepository;

    // 상영시간대
    @Transactional
    public void createShowtime(Showtime showtime) {
        adminShowtimeRepository.save(showtime);
    }

    // 상영시간대 조회
    public List<Showtime> getAllShowtimes() {
        return adminShowtimeRepository.findAll();
    }

    // 상영관 리스트 가져오기
    public List<Screen> getAllScreens() {
        return adminScreenRepository.findAll();
    }

    // 상영관 이름 가져오기
    public List<String> getAllScreenNames() {
        return adminScreenRepository.findAll().stream()
                .map(Screen::getScreenName)
                .collect(Collectors.toList());
    }

    // 상영관 이름 screenId로 매핑해서 가져오기
    public Map<Long, String> getAllScreenNamesById() {
        return adminScreenRepository.findAll().stream()
                .collect(Collectors.toMap(Screen::getId, Screen::getScreenName));
    }

    // 모든 영화 목록 가져오기
    public List<Movie> findAllMovies(){
        List<Movie> movies = adminMovieJpaRepository.findAllMovies();
        return movies;
    }

    public List<String> getAllMovieNames() {
        return adminMovieJpaRepository.findAll().stream()
                .map(Movie::getMovieName)
                .collect(Collectors.toList());
    }

    // 상영관 이름 movieId로 매핑해서 가져오기
    public Map<Long, String> getAllMovieNamesById() {
        return adminMovieJpaRepository.findAll().stream()
                .collect(Collectors.toMap(Movie::getId, Movie::getMovieName));
    }

    public List<Showtime> getShowtimesByScreenId(Long screenId) {
        return adminShowtimeRepository.findByScreenId(screenId);
    }

    // movieID로 상영시간대 조회
    public List<Showtime> getShowtimesByMovieId(Long movieId) {
        return adminShowtimeRepository.findByMovieId(movieId);
    }

    // 상영시간대 추가
    public void addShowtime(Long screenId, Long movieId, LocalDate showtimeDate, LocalTime showtimeTime, int totalSeats, int remainingSeats) {
        Screen screen = adminScreenRepository.findById(screenId).orElseThrow(() -> new IllegalArgumentException("Invalid screen ID"));
        Movie movie = adminMovieJpaRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));

        Showtime showtime = Showtime.builder()
                .screen(screen)
                .movie(movie)
                .showtimeDate(showtimeDate)
                .showtimeTime(showtimeTime)
                .totalSeats(totalSeats)
                .remainingSeats(remainingSeats)
                .build();

        adminShowtimeRepository.save(showtime);
    }

    // 상영관 ID를 통해 예매 가능한 좌석수 반환
    public int countAvailableSeatsByScreenId(Long screenId) {
        return adminSeatRepository.countAvailableSeatsByScreenId(screenId);
    }

    // 상영시간대 삭제
    @Transactional
    public void deleteShowtime(Long id) {
        adminShowtimeRepository.deleteById(id);
    }

    public Showtime getShowtimeById(Long id) {
        return adminShowtimeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid showtime ID"));
    }
}
