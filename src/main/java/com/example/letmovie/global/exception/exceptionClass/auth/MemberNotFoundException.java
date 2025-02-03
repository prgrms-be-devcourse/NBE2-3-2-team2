package com.example.letmovie.global.exception.exceptionClass.auth;

import com.example.letmovie.global.exception.ErrorCodes;
import com.example.letmovie.global.exception.exceptionClass.LetMovieException;

public class MemberNotFoundException extends LetMovieException {
    private static final String MESSAGE = ErrorCodes.MEMBER_NOT_FOUND.getMessage(); // ErrorCodes에서 메시지 가져오기

    public MemberNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return ErrorCodes.MEMBER_NOT_FOUND.getHttpStatus().value();
    }
}