package com.example.letmovie.domain.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDTO {
    private Long movieId; // 영화 아이디
    private String movieName; // 영화 이름
    private String screenName; // 상영관 이름
    private int totalSeats; // 총 좌석
    private int remainingSeats; // 남은 좌석
    private LocalDate showDate; // 상영 날짜
    private LocalTime startTime; // 상영 시작 시간
}