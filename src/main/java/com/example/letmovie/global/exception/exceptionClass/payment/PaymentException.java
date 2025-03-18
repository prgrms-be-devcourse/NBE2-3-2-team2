package com.example.letmovie.global.exception.exceptionClass.payment;

import com.example.letmovie.global.exception.ErrorCodes;
import com.example.letmovie.global.exception.exceptionClass.LetMovieException;
import lombok.Getter;

@Getter
public class PaymentException extends LetMovieException {

    private final ErrorCodes errorCode;

    public PaymentException(ErrorCodes errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    @Override
    public int getStatusCode() {
        return errorCode.getHttpStatus().value();
    }
}
