package com.example.vpn.services;

import com.example.vpn.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<User> getAllUser();

    User fetchData(String accessToken);

    ResponseEntity<Object> deleteOwner(String token);

}
