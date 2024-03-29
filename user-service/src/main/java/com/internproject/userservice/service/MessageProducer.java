package com.internproject.userservice.service;

import com.internproject.userservice.dto.request.SendMailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(Source.class)
public class MessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    private Source source;
    @Autowired
    public MessageProducer(Source source) {
        this.source = source;
    }

    public void send(SendMailRequest sendMailRequest) {
        Message<SendMailRequest> mailMessage = MessageBuilder.withPayload(sendMailRequest).build();
        LOGGER.info("Send JSON message: {}", sendMailRequest.getTo());
        source.output().send(mailMessage);
    }
}
