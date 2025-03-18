package com.example.letmovie.global.exception.exceptionClass.reservation;

import com.example.letmovie.global.exception.ErrorCodes;
import com.example.letmovie.global.exception.exceptionClass.LetMovieException;

public class InvalidSeatPriceException extends LetMovieException {

  private static final String MESSAGE = ErrorCodes.INVALID_SEAT_PRICE.getMessage();

  public InvalidSeatPriceException() {
        super(MESSAGE);
    }

  @Override
  public int getStatusCode() {
    return ErrorCodes.INVALID_SEAT_PRICE.getHttpStatus().value();
  }
}
