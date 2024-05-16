package com.example.vpn.controller;

import com.example.vpn.entities.VerifyCode;
import com.example.vpn.repositories.VerifyCodeRepository;
import com.example.vpn.services.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private VerifyCodeRepository mailRepository;

    @Autowired
    private MailService mailService;
    @PostMapping("/send")
    public ResponseEntity<Object> sendMail(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "message") String message
    ) {
        try {
            mailService.sendMail(email, subject, message);
            return ResponseEntity.ok("Email sent successfully");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email: " + e.getMessage());
        }
    }

    @PostMapping("/sendVerifyCode")
    public ResponseEntity<Object> sendVerifyCode(
            @RequestParam(name = "email") String email
    ) {
        Random rand = new Random();
        int randomNumber = (rand.nextInt(900000) + 100000);

        String verifyCode = Integer.toString(randomNumber);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(calendar.getTime());


        mailRepository.save(new VerifyCode(verifyCode, formattedDate, email));

        return mailService.sendVerifyCode(email, verifyCode);
    }

    @PostMapping("/resendCode")
    public ResponseEntity<Object> resendCode(
            @RequestParam(name = "email") String email
    ) {
        mailRepository.deleteByEmail(email);

        return sendVerifyCode(email);
    }
}
