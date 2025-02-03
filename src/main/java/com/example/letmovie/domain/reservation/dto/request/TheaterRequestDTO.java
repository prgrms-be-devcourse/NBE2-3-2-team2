package com.example.letmovie.domain.reservation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TheaterRequestDTO {
    private String movieName;
    private String date;
}
