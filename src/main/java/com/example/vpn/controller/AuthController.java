package com.example.vpn.controller;

import com.example.vpn.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password
    ) {
        return authService.login(username, password);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "role") String role
    ) {
        return authService.register(username, email, password, role);
    }
}
