package com.example.letmovie.global.exception.exceptionClass.payment;

import com.example.letmovie.global.exception.ErrorCodes;
import com.example.letmovie.global.exception.exceptionClass.LetMovieException;
import lombok.Getter;

@Getter
public class VerificationCodeException extends LetMovieException {

  private final ErrorCodes errorCode;

    public VerificationCodeException(ErrorCodes errorCode) {
      super(errorCode.getMessage());
      this.errorCode = errorCode;
    }

  @Override
  public int getStatusCode() {
    return errorCode.getHttpStatus().value();
  }
}
