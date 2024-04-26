package com.example.vpn.services;

import com.example.vpn.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    public List<User> getAllUser();

}
