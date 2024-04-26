package com.example.vpn.services.impl;

import com.example.vpn.entities.User;
import com.example.vpn.repositories.UserRepository;
import com.example.vpn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
