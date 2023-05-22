package com.internproject.mailservice.controller;

import com.internproject.mailservice.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send-mail")
public class SendMailController {
    private SendMailService sendMailService;

    @Autowired
    public SendMailController(SendMailService sendMailService) {
        this.sendMailService = sendMailService;
    }

    @PostMapping
    public void sendMail() {
        sendMailService.sendEmail("lehobaokhang2611@gmail.com", "Test", "test");
    }
}
