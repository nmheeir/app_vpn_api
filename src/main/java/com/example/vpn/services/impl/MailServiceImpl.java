package com.example.vpn.services.impl;

import com.example.vpn.responses.OtherResponse;
import com.example.vpn.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value(value = "${spring.mail.username}")
    private String fromMail;

    @Override
    public ResponseEntity<Object> sendMail(String email, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo(email);

        try {
            mailSender.send(simpleMailMessage);
            return OtherResponse.successResponseBuilder(HttpStatus.OK, "Send mail success");
        } catch (MailException e) {
            return OtherResponse.errorResponseBuilder(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> sendVerifyCode(String email, String code) {
        String subject = "Verify code";
        String message = "Your verify code is " + code;

        return sendMail(email, subject, message);
    }
}
