package com.example.vpn.services;

import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<Object> login(String username, String password);

    ResponseEntity<Object> register(String username, String password, String email, String role);

    ResponseEntity<Object> refreshToken(String accessToken);

    ResponseEntity<Object> verifyEmail(String email, String verifyCode);

    ResponseEntity<Object> checkUsernameEmail(String username, String email);
}