package com.example.vpn.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException {
    private final Boolean isSuccessful;


    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;

    public UserException(Boolean isSuccessful, String message, Throwable throwable, HttpStatus httpStatus) {
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }
}