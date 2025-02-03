package com.example.letmovie.domain.movie.service;

import com.example.letmovie.domain.movie.dto.ShowtimeDTO;
import com.example.letmovie.domain.movie.entity.Showtime;
import com.example.letmovie.domain.movie.repository.ShowtimeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowtimeServiceImpl {

    private final ShowtimeJpaRepository showtimeJpaRepository;

    public List<ShowtimeDTO> getAllShowtime() {
        List<Showtime> showtimes = showtimeJpaRepository.findAll();
        return showtimes.stream()
                .map(showtime -> new ShowtimeDTO(
                        showtime.getId(),
                        showtime.getMovie().getMovieName(),
                        showtime.getScreen().getScreenName(),
                        showtime.getTotalSeats(),
                        showtime.getRemainingSeats(),
                        showtime.getShowtimeDate(),
                        showtime.getShowtimeTime()))
                .collect(Collectors.toList());
    }

}
