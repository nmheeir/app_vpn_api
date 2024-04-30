package com.example.vpn.exception;

import org.springframework.http.HttpStatus;

public record UserException(Boolean isSuccessful, String message, Throwable throwable, HttpStatus httpStatus) {
}