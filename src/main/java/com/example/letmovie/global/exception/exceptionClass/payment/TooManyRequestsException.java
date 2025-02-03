package com.example.letmovie.global.exception.exceptionClass.payment;

import com.example.letmovie.global.exception.ErrorCodes;
import com.example.letmovie.global.exception.exceptionClass.LetMovieException;
import org.springframework.http.HttpStatus;

public class TooManyRequestsException extends LetMovieException {

    public TooManyRequestsException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.TOO_MANY_REQUESTS.value();
    }
}
