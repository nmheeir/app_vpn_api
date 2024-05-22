package com.example.vpn.services.impl;

import com.example.vpn.entities.VerifyCode;
import com.example.vpn.repositories.VerifyCodeRepository;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value(value = "${spring.mail.username}")
    private String fromMail;

    @Autowired
    private VerifyCodeRepository mailRepository;

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
    public ResponseEntity<Object> sendVerifyCode(String email) {
        Random rand = new Random();
        int randomNumber = (rand.nextInt(900000) + 100000);

        String verifyCode = Integer.toString(randomNumber);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(calendar.getTime());


        mailRepository.save(new VerifyCode(verifyCode, formattedDate, email));

        String subject = "Verify code";
        String message = Utils.verifyCodeForm(verifyCode);

        return sendMail(email, subject, message);
    }
}
