package com.example.vpn.services;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface MailService {

    ResponseEntity<Object> sendMail(String email, String subject, String message) throws MessagingException;

    ResponseEntity<Object> sendVerifyCode(String email);
}
