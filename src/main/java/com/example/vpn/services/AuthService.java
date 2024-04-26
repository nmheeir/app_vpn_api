package com.example.vpn.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.ResponseCache;

public interface AuthService {

    ResponseEntity<Object> login(String username, String password);

    ResponseEntity<Object> register(String username, String password, String email, String role);

    ResponseEntity<Object> refreshToken(String accessToken);
}
