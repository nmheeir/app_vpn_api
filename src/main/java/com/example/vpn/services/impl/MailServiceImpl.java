package com.example.vpn.services.impl;

import com.example.vpn.responses.OtherResponse;
import com.example.vpn.services.MailService;
import com.example.vpn.utils.Utils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value(value = "${spring.mail.username}")
    private String fromMail;

    @Override
    public ResponseEntity<Object> sendMail(String email, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);

            mailSender.send(mimeMessage);

            return OtherResponse.successResponseBuilder(HttpStatus.OK, "Send mail success");

        } catch (MessagingException | MailException e) {
            return OtherResponse.errorResponseBuilder(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
    }


    @Override
    public ResponseEntity<Object> sendVerifyCode(String email, String code) {
        String subject = "Verify code";
        String message = Utils.verifyCodeForm(code);

        return sendMail(email, subject, message);
    }
}
