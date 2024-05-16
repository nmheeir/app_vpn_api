package com.example.vpn.services.impl;

import com.example.vpn.entities.User;
import com.example.vpn.repositories.PaymentRepository;
import com.example.vpn.services.PaymentService;
import com.example.vpn.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public String getPremiumKey(User user, Integer time) {
        return jwtUtils.generatePremiumKey(user.getUsername(), time);
    }
}
