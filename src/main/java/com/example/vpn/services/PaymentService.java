package com.example.vpn.services;

import com.example.vpn.entities.User;
import org.springframework.http.ResponseEntity;

public interface PaymentService {

    String getPremiumKey(User user, Integer time);

}
