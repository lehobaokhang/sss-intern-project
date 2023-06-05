package com.internproject.shippingservice.service;

import com.internproject.shippingservice.dto.ShipDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableBinding(Sink.class)
public class ShipMessageListener {
    private ShipService shipService;
    public static final Logger logger = LoggerFactory.getLogger(ShipMessageListener.class);


    @Autowired
    public ShipMessageListener(ShipService shipService) {
        this.shipService = shipService;
    }

    @StreamListener(Sink.INPUT)
    public void receiveOrderMessage(List<ShipDTO> ships) {
        logger.info(String.format("Receive List<ShipDTO> from message queue -> size: %d", ships.size()));
        shipService.createShip(ships);
    }
}
