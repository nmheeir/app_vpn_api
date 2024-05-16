package com.example.vpn.services;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface MailService {

    public ResponseEntity<Object> sendMail(String email, String subject, String message) throws MessagingException;

    public ResponseEntity<Object> sendVerifyCode(String email, String code);
}
