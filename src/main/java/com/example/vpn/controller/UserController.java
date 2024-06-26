package com.example.vpn.controller;

import com.example.vpn.entities.User;
import com.example.vpn.repositories.UserRepository;
import com.example.vpn.responses.DataResponse;
import com.example.vpn.responses.OtherResponse;
import com.example.vpn.services.UserService;
import com.example.vpn.utils.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public ResponseEntity<Object> detail(String token) {
        return null;
    }

    @GetMapping("/detail")
    public ResponseEntity<Object> fetchData(
            @RequestHeader(value = "Authorization") String token
     ) {
        if (jwtUtils.isTokenValid(token)) {
            String username = jwtUtils.extractUsername(token);

            User user = userRepository.findUserByUsername(username);

            return DataResponse.dataResponseBuilder(true, "Successful", HttpStatus.OK, user);
        }
        return OtherResponse.errorResponseBuilder(HttpStatus.NOT_FOUND, "Can't found user");
    }

    @PostMapping("/changepw")
    public ResponseEntity<Object> changePassword(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(name = "oldPassword") String oldPassword,
            @RequestParam(name = "newPassword") String newPassword
    ) {
        if (jwtUtils.isTokenValid(token)) {
            String username = jwtUtils.extractUsername(token);
            User user = userRepository.findUserByUsername(username);

            if (BCrypt.checkpw(oldPassword, user.getPassword())) {
                user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
                userRepository.save(user);

                return DataResponse.dataResponseBuilder(true, "Successful change password", HttpStatus.OK, user);
            }
            else {
                return OtherResponse.errorResponseBuilder(HttpStatus.OK, "Incorrect password");
            }
        }
        return OtherResponse.errorResponseBuilder(HttpStatus.CONFLICT, "Can't update user");
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password
    ) {
        return userService.resetPassword(email, password);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteOwn(
            @RequestHeader(value = "Authorization") String token
    ) {
        return userService.deleteOwner(token);
    }

    @PostMapping("/premiumType")
    public ResponseEntity<Object> premiumType(
            @RequestParam(name = "premiumKey") String key
    ) {
        return DataResponse.dataResponseBuilder(true, "Ok", HttpStatus.OK, jwtUtils.extractPremiumType(key));
    }

}
