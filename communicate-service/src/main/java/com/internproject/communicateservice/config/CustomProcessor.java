package com.internproject.communicateservice.config;

import com.internproject.communicateservice.constant.RabbitMQConstant;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CustomProcessor {
    @Input(RabbitMQConstant.MAIL_INPUT)
    SubscribableChannel mailInput();

    @Input(RabbitMQConstant.ORDER_INPUT)
    SubscribableChannel orderInput();

    @Output(RabbitMQConstant.MAIL_OUTPUT)
    MessageChannel mailOutput();

    @Output(RabbitMQConstant.ORDER_OUTPUT)
    MessageChannel orderOutput();
}
