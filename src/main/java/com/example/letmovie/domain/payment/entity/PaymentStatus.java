package com.example.letmovie.domain.payment.entity;

public enum PaymentStatus {
    AWAITING_PAYMENT, /* 결제 대기 */
    PAYMENT_SUCCESS,
    PAYMENT_FAILED,
    PAYMENT_CANCELLED, /* 결제 취소*/
    CANCEL_PAYMENT,
}