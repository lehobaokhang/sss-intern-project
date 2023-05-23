package com.internproject.mailservice.service;

import com.internproject.mailservice.dto.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailService {
    private final JavaMailSender mailSender;

    @Autowired
    public SendMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailDetails emailDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDetails.getTo());
        message.setSubject(emailDetails.getSubject());
        message.setText(String.format("Hello %s! %s", emailDetails.getFullName(), emailDetails.getMessage()));
        mailSender.send(message);
    }
}
