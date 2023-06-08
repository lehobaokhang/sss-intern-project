package com.internproject.communicateservice.service;

import com.internproject.communicateservice.config.CustomProcessor;
import com.internproject.communicateservice.constant.RabbitMQConstant;
import com.internproject.communicateservice.dto.SendMailRequest;
import com.internproject.communicateservice.dto.ShipDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.List;

@EnableBinding(CustomProcessor.class)
public class MessageReceiver {
    public static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

    @StreamListener(RabbitMQConstant.MAIL_INPUT)
    @SendTo(RabbitMQConstant.MAIL_OUTPUT)
    public SendMailRequest processMailQueue(SendMailRequest sendMailRequest) {
        String sendMailRequestToString = sendMailRequest.toString();
        LOGGER.info("Processing JSON message -> {}",sendMailRequestToString);
        return sendMailRequest;
    }

    @StreamListener(RabbitMQConstant.ORDER_INPUT)
    @SendTo(RabbitMQConstant.ORDER_OUTPUT)
    public List<ShipDTO> processOrderQueue(List<ShipDTO> ships) {
        for (ShipDTO ship : ships) {
            String shipToString = ship.toString();
            LOGGER.info(shipToString);
        }
        return ships;
    }
}
