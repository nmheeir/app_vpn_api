package com.example.vpn.services;

import com.example.vpn.entities.User;

import java.util.List;

public interface UserService {

    List<User> getAllUser();

    User fetchData(String accessToken);

}
