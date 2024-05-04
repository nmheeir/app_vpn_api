package com.example.vpn.controller;

import com.example.vpn.entities.VerifyCode;
import com.example.vpn.repositories.VerifyCodeRepository;
import com.example.vpn.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return mailService.sendMail(email, subject, message);
    }

    @PostMapping("/sendVerifyCode")
    public ResponseEntity<Object> sendVerifyCode(
            @RequestParam(name = "email") String email
    ) {
        Random rand = new Random();
        int randomNumber = (rand.nextInt(900000) + 100000);

        String verifyCode = Integer.toString(randomNumber);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(calendar.getTime());


        mailRepository.save(new VerifyCode(verifyCode, formattedDate, email));

        return mailService.sendVerifyCode(email, verifyCode);
    }
}