package com.example.vpn.services.impl;

import com.example.vpn.entities.User;
import com.example.vpn.entities.VerifyCode;
import com.example.vpn.repositories.UserRepository;
import com.example.vpn.repositories.VerifyCodeRepository;
import com.example.vpn.responses.DataResponse;
import com.example.vpn.responses.OtherResponse;
import com.example.vpn.services.AuthService;
import com.example.vpn.utils.JwtUtils;
import com.example.vpn.utils.Utils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerifyCodeRepository verifyCodeRepository;

    @Override
    public ResponseEntity<Object> login(String username, String password) {

        User user = userRepository.findUserByUsername(username);

        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                Map<String, Object> map = new HashMap<>();
                map.put("accessToken", jwtUtils.generateAccessToken(user));
                map.put("refreshToken", jwtUtils.generateRefreshToken(new HashMap<>(), user));
                map.put("premiumKey", user.getPremiumKey());
                return DataResponse.dataResponseBuilder(true, "Login Success", HttpStatus.OK, map);
            }
            return OtherResponse.errorResponseBuilder(HttpStatus.OK, "Wrong password");
        }
        return OtherResponse.errorResponseBuilder(HttpStatus.OK, "User doesn't exist");
    }

    @Override
    public ResponseEntity<Object> register(String username, String email, String password, String role) {
        try {
            String passwordEncode = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(username, email, passwordEncode, role, jwtUtils.generateFreeKey(username));
            user.setVerifyAt(Utils.getCurrentTime());
            User savedUser = userRepository.save(user);
            return DataResponse.dataResponseBuilder(true, "Register Success", HttpStatus.OK, savedUser);
        } catch (DataIntegrityViolationException e) {
            // Xử lý lỗi khi thêm dữ liệu đã tồn tại vào cơ sở dữ liệu
            return OtherResponse.errorResponseBuilder(HttpStatus.OK, "User is already exist");
        }
    }

    @Override
    public ResponseEntity<Object> refreshToken(String accessToken) {
        String username = jwtUtils.extractUsername(accessToken);
        User user = userRepository.findUserByUsername(username);
        String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
        return null;
    }

    @Override
    public ResponseEntity<Object> verifyEmail(String email, String verifyCode) {

        VerifyCode isExist = verifyCodeRepository.check(email, verifyCode);

        if (isExist != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime expire_time = LocalDateTime.parse(isExist.getExpire(), formatter);

            LocalDateTime time_now = LocalDateTime.now();

            if (time_now.isAfter(expire_time)) {
                return OtherResponse.errorResponseBuilder(HttpStatus.OK, "Verification code has expired");
            }
            verifyCodeRepository.delete(isExist);
            return OtherResponse.successResponseBuilder(HttpStatus.OK, "Verification successful");
        }
        return OtherResponse.errorResponseBuilder(HttpStatus.OK, "Invalid verification code");
    }

    @Override
    public ResponseEntity<Object> checkUsernameEmail(String username, String email) {
        User user_username = userRepository.findUserByUsername(username);
        if (user_username != null) {
            return OtherResponse.errorResponseBuilder(HttpStatus.OK, "Username is already exist");
        }

        User user_email = userRepository.findUserByEmail(email);
        if (user_email != null) {
            return OtherResponse.errorResponseBuilder(HttpStatus.OK, "Email is already exist");
        }
        return OtherResponse.successResponseBuilder(HttpStatus.OK, "Valid");
    }

    @Override
    public ResponseEntity<Object> checkEmail(String email) {
        User user_email = userRepository.findUserByEmail(email);
        if (user_email == null) {
            return OtherResponse.errorResponseBuilder(HttpStatus.OK, "Email does not exist");
        }
        return OtherResponse.successResponseBuilder(HttpStatus.OK, "Valid");
    }
}
