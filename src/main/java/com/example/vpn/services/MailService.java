package com.example.vpn.services;

import org.springframework.http.ResponseEntity;

public interface MailService {

    public ResponseEntity<Object> sendMail(String email, String subject, String message);

    public ResponseEntity<Object> sendVerifyCode(String email, String code);
}
