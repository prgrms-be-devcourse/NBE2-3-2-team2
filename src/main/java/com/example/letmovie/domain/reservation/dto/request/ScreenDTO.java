package com.example.letmovie.domain.reservation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenDTO {
    private Long id;
    private String screenName;

    @NotNull(message = "Theater ID는 필수입니다.")
    private Long theaterId;
}
