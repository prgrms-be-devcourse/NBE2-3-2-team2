package com.example.letmovie.domain.reservation.service;

import com.example.letmovie.domain.movie.entity.Showtime;
import com.example.letmovie.domain.movie.entity.Theater;
import com.example.letmovie.domain.reservation.dto.response.MovieNamesResponseDTO;
import com.example.letmovie.domain.reservation.dto.response.ShowTimeResponseDTO;
import com.example.letmovie.domain.reservation.dto.response.TheaterResponseDTO;
import com.example.letmovie.domain.reservation.entity.Seat;
import com.example.letmovie.domain.reservation.repository.ShowtimeQueryRepository;
import com.example.letmovie.domain.reservation.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final ShowtimeQueryRepository showtimeQueryRepository;

    /**
     *  날짜로 영화 이름 가져오기.
     */
    public MovieNamesResponseDTO findMovieNameByDate(String date) {
        return new MovieNamesResponseDTO(showtimeRepository.findDistinctMovieNamesByDate(LocalDate.parse(date)));
    }

    /**
     * 영화 이름, 날짜로 극장 가져오기.
     */
    public List<TheaterResponseDTO> findTheatersByMovieNameAndDate(String movieName, String date) {
        List<Theater> theaters = showtimeRepository.findTheatersByMovieNameAndShowtimeDate(movieName, LocalDate.parse(date));
        return theaters.stream()
                .map(theater -> new TheaterResponseDTO(theater.getId(), theater.getTheaterName()))
                .toList();
    }

    /**
     * 영화 날짜, 영화, 극장 선택으로 상영관,시간대 찾기.
     */
    public List<ShowTimeResponseDTO> findShowtimeByDateAndMovieNameAndTheater(String movieName, String date, String theaterName) {
        LocalDate showtimeDate = LocalDate.parse(date);
        boolean isToday = showtimeDate.equals(LocalDate.now());

        List<Showtime> showtimes = showtimeQueryRepository.findShowtimesByMovieNameAndShowtimeDateAndTheaterName(theaterName, movieName, showtimeDate, isToday);

        return showtimes.stream()
                .map(showtime -> new ShowTimeResponseDTO(
                        showtime.getScreen().getTheater().getTheaterName(),
                        showtime.getScreen().getScreenName(),
                        showtime.getTotalSeats(),
                        showtime.getRemainingSeats(),
                        showtime.getShowtimeTime().toString(),
                        showtime.getId()
                ))
                .toList();
    }

    public Optional<Showtime> findById(Long id) {
        return showtimeRepository.findById(id);
    }

    /**
     * 좌석 리스트를 행(A, B, C...)별로 그룹화하는 메서드.
     */
    public Map<String, List<Seat>> convertSeatsToRowMap(List<Seat> seats) {
        if (seats == null || seats.isEmpty()) {
            return Collections.emptyMap();
        }

        // 최대 행 계산
        int maxRow = seats.stream()
                .mapToInt(Seat::getSeatLow) // seatLow 값 추출
                .max() // 가장 큰 값 찾기
                .orElse(0);

        // rows를 알파벳 리스트로 변환 ["A", "B", "C", ...]
        List<String> rowLabels = IntStream.rangeClosed(0, maxRow - 1)
                .mapToObj(i -> String.valueOf((char) ('A' + i))) // 숫자를 A, B, C로 변환
                .collect(Collectors.toList());

        // Map<String, List<Seat>> 구조로 변환
        return rowLabels.stream()
                .collect(Collectors.toMap(
                        row -> row, // 키: A, B, C, ...
                        row -> seats.stream()
                                .filter(seat -> seat.getSeatLow() == row.charAt(0) - 'A' + 1) // 예: seatLow = 1 → "A"
                                .collect(Collectors.toList())
                ));
    }


}
