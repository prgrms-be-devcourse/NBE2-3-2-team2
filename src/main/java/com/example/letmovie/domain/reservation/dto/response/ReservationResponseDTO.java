package com.example.letmovie.domain.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservationResponseDTO {

    private Long reservationId;
    private Long memberId;
    private String memberName;
    private int totalPrice;
}
