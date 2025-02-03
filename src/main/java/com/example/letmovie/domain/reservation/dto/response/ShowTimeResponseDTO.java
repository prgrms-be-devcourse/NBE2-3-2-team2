package com.example.letmovie.domain.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShowTimeResponseDTO {
    private String theaterName;       // 극장이름
    private String screenName;        // 상영관 이름
    private int screenTotalSeat;   // 상영관 전체 좌석
    private int screenRemainSeat;  // 상영관 예약 가능 좌석
    private String showtime;          // 상영 시작 시간
    private Long showtimeId;        // 쇼타임 ID
}