package com.example.letmovie.global.exception.exceptionClass.reservation;

import com.example.letmovie.global.exception.ErrorCodes;
import com.example.letmovie.global.exception.exceptionClass.LetMovieException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeatNotFoundException extends LetMovieException {

    private static final String MESSAGE = ErrorCodes.SEAT_NOT_FOUND.getMessage();

    public SeatNotFoundException() {
        super(MESSAGE);
    }

    public SeatNotFoundException(String message) {
        super(message);
        log.info("메시지 : = {} ",message);
    }

    @Override
    public int getStatusCode() {
        return ErrorCodes.SEAT_NOT_FOUND.getHttpStatus().value();
    }
}
