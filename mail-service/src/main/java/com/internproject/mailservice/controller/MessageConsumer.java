package com.internproject.mailservice.controller;

import com.internproject.mailservice.dto.EmailDetails;
import com.internproject.mailservice.service.SendMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
public class MessageConsumer {
    private SendMailService sendMailService;
    public static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    public MessageConsumer(SendMailService sendMailService) {
        this.sendMailService = sendMailService;
    }

    @StreamListener(Sink.INPUT)
    public void receiver(EmailDetails emailDetails) {
        String emailDetailsToString = emailDetails.toString();
        logger.info("Receive JSON message queue -> {}", emailDetailsToString);
        sendMailService.sendEmail(emailDetails);
    }
}
