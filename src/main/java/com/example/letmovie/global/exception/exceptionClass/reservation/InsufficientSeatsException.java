package com.example.letmovie.global.exception.exceptionClass.reservation;

import com.example.letmovie.global.exception.ErrorCodes;
import com.example.letmovie.global.exception.exceptionClass.LetMovieException;

public class InsufficientSeatsException extends LetMovieException {

  private static final String MESSAGE = ErrorCodes.SEAT_NOT_ENOUGH.getMessage();


  public InsufficientSeatsException() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return ErrorCodes.SEAT_NOT_ENOUGH.getHttpStatus().value();
  }
}
