package com.example.vpn.services.impl;

import com.example.vpn.entities.User;
import com.example.vpn.utils.JwtUtils;
import com.example.vpn.repositories.UserRepository;
import com.example.vpn.responses.AuthResponse;
import com.example.vpn.services.AuthService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseEntity<Object> login(String username, String password) {

        User user = userRepository.findUserByUsername(username);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            String accessToken = jwtUtils.generateAccessToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            return AuthResponse.authResponseBuilder(true, HttpStatus.OK, "Login OK", accessToken, refreshToken);
        }

        return AuthResponse.authResponseBuilder(false ,HttpStatus.NOT_FOUND, "Login not ok", null, null);
    }

    @Override
    public ResponseEntity<Object> register(String username, String email, String password, String role) {
        String passwordEncode = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(username, email, passwordEncode, role);

        userRepository.save(user);

        return AuthResponse.registerResponseBuilder(true, HttpStatus.OK, "Register Ok", user);
    }

    @Override
    public ResponseEntity<Object> refreshToken(String accessToken) {
        String username = jwtUtils.extractUsername(accessToken);

        User user = userRepository.findUserByUsername(username);



        String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

        return null;
    }
}
