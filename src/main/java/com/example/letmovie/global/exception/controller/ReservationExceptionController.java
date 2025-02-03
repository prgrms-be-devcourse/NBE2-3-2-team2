package com.example.letmovie.global.exception.controller;

import com.example.letmovie.global.exception.ErrorResponse;
import com.example.letmovie.global.exception.exceptionClass.LetMovieException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ControllerAdvice
public class ReservationExceptionController {

    @ResponseBody
    @ExceptionHandler(LetMovieException.class)
    public ResponseEntity<ErrorResponse> LetMovieException(LetMovieException e){
        int statusCode = e.getStatusCode();

        ErrorResponse body = new ErrorResponse(String.valueOf(statusCode),e.getMessage());

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                .body(body);

        return response;
    }
}
