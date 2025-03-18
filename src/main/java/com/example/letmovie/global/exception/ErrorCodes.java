package com.example.letmovie.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCodes {

    //member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다"),

    // Payment
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND,  "결제 정보를 찾을 수 없습니다."),
    PAYMENT_ALREADY_CANCELLED(HttpStatus.BAD_REQUEST, "이미 취소된 결제입니다."),
    PAYMENT_FAILED(HttpStatus.BAD_REQUEST,  "결제 처리 중 오류가 발생했습니다."),
    INVALID_PAYMENT_STATUS(HttpStatus.BAD_REQUEST, "잘못된 결제 상태입니다."),

    // Reservation
    SHOWTIME_NOT_FOUND(HttpStatus.NOT_FOUND, "상영 정보를 찾을 수 없습니다."),
    SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "좌석을 찾을 수 없습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약 정보를 찾을 수 없습니다."),
    RESERVATION_CANCELLATION_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "상영 완료된 영화는 취소가 불가능합니다."),
    SEAT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "요청한 좌석 수보다 남은 좌석이 적습니다"),
    SEAT_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "취소 시 남은 좌석이 전체 좌석 수를 초과합니다"),
    INVALID_SEAT_PRICE(HttpStatus.BAD_REQUEST,"좌석 가격이 유효하지 않습니다."),

    // VerificationCode
    EMAIL_SEND_FAILED(HttpStatus.BAD_REQUEST, "이메일 전송 중 오류가 발생했습니다."),
    VERIFICATION_CODE_SAVE_FAILED(HttpStatus.BAD_REQUEST, "인증 코드 저장에 실패했습니다."),
    UNEXPECTED_ERROR(HttpStatus.BAD_REQUEST, "알 수 없는 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
