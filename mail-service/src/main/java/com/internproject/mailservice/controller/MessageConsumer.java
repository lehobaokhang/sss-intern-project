package com.internproject.mailservice.controller;

import com.internproject.mailservice.dto.EmailDetails;
import com.internproject.mailservice.service.SendMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    private SendMailService sendMailService;
    public static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    public MessageConsumer(SendMailService sendMailService) {
        this.sendMailService = sendMailService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue}"})
    public void receiver(EmailDetails emailDetails) {
        logger.info(String.format("Receive JSON message queue -> %s", emailDetails.toString()));
        sendMailService.sendEmail(emailDetails);
    }
}
