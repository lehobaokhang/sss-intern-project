package com.internproject.userservice.service;

import com.internproject.userservice.dto.request.SendMailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey}")
    private String routingKey;
    private RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(SendMailRequest sendMailRequest) {
        logger.info(String.format("Send JSON message: %s", sendMailRequest.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, sendMailRequest);
    }
}
