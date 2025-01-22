package com.example.letmovie.global.exception.exceptionClass.reservation;

import com.example.letmovie.global.exception.ErrorCodes;
import com.example.letmovie.global.exception.exceptionClass.LetMovieException;

public class ReservationCancellation extends LetMovieException {

    private static final String MESSAGE = ErrorCodes.RESERVATION_CANCELLATION_NOT_ALLOWED.getMessage();

    public ReservationCancellation() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return ErrorCodes.RESERVATION_CANCELLATION_NOT_ALLOWED.getHttpStatus().value();
    }

}
