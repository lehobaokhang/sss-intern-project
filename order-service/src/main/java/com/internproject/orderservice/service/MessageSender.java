package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.ShipDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableBinding(Source.class)
public class MessageSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);
    private Source source;
    @Autowired
    public MessageSender(Source source) {
        this.source = source;
    }

    public void sendOrderMessage(List<ShipDTO> ships) {
        Message<List<ShipDTO>> shipMessage = MessageBuilder.withPayload(ships).build();
        source.output().send(shipMessage);
        LOGGER.info("Send List<ShipDTO> to message queue -> size: {}", ships.size());
    }
}
