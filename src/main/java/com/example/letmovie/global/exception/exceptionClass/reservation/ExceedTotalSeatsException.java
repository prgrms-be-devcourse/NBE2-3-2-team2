package com.example.letmovie.global.exception.exceptionClass.reservation;

import com.example.letmovie.global.exception.ErrorCodes;
import com.example.letmovie.global.exception.exceptionClass.LetMovieException;

public class ExceedTotalSeatsException extends LetMovieException {

  private static final String MESSAGE = ErrorCodes.SEAT_LIMIT_EXCEEDED.getMessage();


  public ExceedTotalSeatsException() {
    super(MESSAGE);
  }

  @Override
  public int getStatusCode() {
    return ErrorCodes.SEAT_LIMIT_EXCEEDED.getHttpStatus().value();
  }

}