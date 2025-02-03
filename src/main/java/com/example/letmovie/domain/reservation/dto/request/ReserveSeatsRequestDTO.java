package com.example.letmovie.domain.reservation.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReserveSeatsRequestDTO {
    private List<String> seats; // 좌석 목록
    private Long showtimeId; // 쇼타임 ID
}