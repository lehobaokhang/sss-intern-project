package com.internproject.communicateservice.service;

import com.internproject.communicateservice.config.CustomProcessor;
import com.internproject.communicateservice.constant.RabbitMQConstant;
import com.internproject.communicateservice.dto.SendMailRequest;
import com.internproject.communicateservice.dto.ShipDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.List;

@EnableBinding(CustomProcessor.class)
public class MessageReceiver {
    public static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

    private CustomProcessor customProcessor;

    @Autowired
    public MessageReceiver(CustomProcessor customProcessor) {
        this.customProcessor = customProcessor;
    }

    @StreamListener(RabbitMQConstant.MAIL_INPUT)
    @SendTo(RabbitMQConstant.MAIL_OUTPUT)
    public SendMailRequest processMailQueue(SendMailRequest sendMailRequest) {
        LOGGER.info("Processing JSON message -> {}",sendMailRequest.toString());
        return sendMailRequest;
    }

    @StreamListener(RabbitMQConstant.ORDER_INPUT)
    @SendTo(RabbitMQConstant.ORDER_OUTPUT)
    public List<ShipDTO> processOrderQueue(List<ShipDTO> ships) {
        for (ShipDTO ship : ships) {
            LOGGER.info(ship.toString());
        }
        return ships;
    }
}
