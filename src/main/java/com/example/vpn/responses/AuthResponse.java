package com.example.vpn.responses;

import com.example.vpn.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class AuthResponse {

    public static ResponseEntity<Object> authResponseBuilder(
            Boolean successful,
            HttpStatus httpStatus,
            String message,
            String accessToken,
            String refreshToken
    ) {
        HashMap<String, Object> authResponse = new HashMap<>();
        authResponse.put("isSuccessful", successful);
        authResponse.put("httpStatus", httpStatus);
        authResponse.put("message", message);
        authResponse.put("accessToken", accessToken);
        authResponse.put("refreshToken", refreshToken);

        return new ResponseEntity<>(authResponse, httpStatus);
    }

    public static ResponseEntity<Object> registerResponseBuilder(
            Boolean successful,
            HttpStatus httpStatus,
            String message,
            User user
    ) {
        HashMap<String, Object> registerResponse = new HashMap<>();
        registerResponse.put("isSuccessful", successful);
        registerResponse.put("httpStatus", httpStatus);
        registerResponse.put("message", message);
        registerResponse.put("user", user);

        return new ResponseEntity<>(registerResponse, httpStatus);

    }
}
