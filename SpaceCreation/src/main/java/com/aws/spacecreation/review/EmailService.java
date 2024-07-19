package com.aws.spacecreation.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender daumMailSender;

    @Value("${spring.mail.daum.username}")
    private String daumFromEmail;

    public void sendEmailFromDaum(Question question) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(daumFromEmail);
        message.setTo(daumFromEmail);
        message.setSubject(question.getSubject());
        message.setText(question.getContent());
        daumMailSender.send(message);
    }
}
