package com.example.letmovie.global.exception.exceptionClass;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class LetMovieException extends RuntimeException {

    private final Map<String,String> validation = new HashMap<>();

    public LetMovieException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName,String message){
        validation.put(fieldName,message);
    }
}
