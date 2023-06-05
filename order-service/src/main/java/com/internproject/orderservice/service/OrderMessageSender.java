package com.internproject.orderservice.service;

import com.internproject.orderservice.dto.ShipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableBinding(Source.class)
public class OrderMessageSender {
    private Source source;

    @Autowired
    public OrderMessageSender(Source source) {
        this.source = source;
    }

    public void sendOrderMessage(List<ShipDTO> ships) {
        source.output().send(MessageBuilder.withPayload(ships).build());
    }
}
