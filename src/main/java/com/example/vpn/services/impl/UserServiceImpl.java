package com.example.vpn.services.impl;

import com.example.vpn.entities.User;
import com.example.vpn.repositories.UserRepository;
import com.example.vpn.responses.OtherResponse;
import com.example.vpn.services.UserService;
import com.example.vpn.utils.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User fetchData(String accessToken) {
        String username = jwtUtils.extractUsername(accessToken);

        return userRepository.findUserByUsername(username);
    }

    @Override
    public ResponseEntity<Object> deleteOwner(String token) {
        Integer uid = jwtUtils.extractUid(token);
        try {
            userRepository.deleteById(uid);
            return OtherResponse.successResponseBuilder(HttpStatus.OK, "Delete user successful");
        } catch (Exception e) {
            return OtherResponse.errorResponseBuilder(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> resetPassword(String email, String password) {
        try {
            User user = userRepository.findUserByEmail(email);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            userRepository.save(user);
            return OtherResponse.successResponseBuilder(HttpStatus.OK, "Reset password successful");
        } catch (EmptyResultDataAccessException e) {
            return OtherResponse.errorResponseBuilder(HttpStatus.NOT_FOUND, "Can't found user to reset password");
        }
    }
}
