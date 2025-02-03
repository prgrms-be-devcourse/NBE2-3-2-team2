package com.example.letmovie.domain.reservation.entity;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    PENDING("예매 중"),
    CANCELLED("취소"),
    COMPLETED("예매 완료"),
    VIEWED("상영 확인");

    private final String displayName;

    ReservationStatus(String displayName) {
        this.displayName = displayName;
    }
}
