package com.example.vpn.services.impl;

import com.example.vpn.entities.User;
import com.example.vpn.repositories.UserRepository;
import com.example.vpn.responses.AuthResponse;
import com.example.vpn.services.AuthService;
import com.example.vpn.utils.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

        User user_username = userRepository.findUserByUsername(username);
        if (user_username != null) {
            return AuthResponse.registerResponseBuilder(false, HttpStatus.CONFLICT, "Đã tồn tại username", null);
        }

        User user_email = userRepository.findUserByEmail(email);
        if (user_email != null) {
            return AuthResponse.registerResponseBuilder(false, HttpStatus.CONFLICT, "Đã tồn tại email", null);
        }

        try {
            String passwordEncode = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(username, email, passwordEncode, role);
            User savedUser = userRepository.save(user);
            return AuthResponse.registerResponseBuilder(true, HttpStatus.OK, "Register Ok", savedUser);
        } catch (DataIntegrityViolationException e) {
            // Xử lý lỗi khi thêm dữ liệu đã tồn tại vào cơ sở dữ liệu
            return AuthResponse.registerResponseBuilder(false, HttpStatus.CONFLICT, "User already exists. ", null);
        }
    }

    @Override
    public ResponseEntity<Object> refreshToken(String accessToken) {
        String username = jwtUtils.extractUsername(accessToken);

        User user = userRepository.findUserByUsername(username);



        String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

        return null;
    }
}
