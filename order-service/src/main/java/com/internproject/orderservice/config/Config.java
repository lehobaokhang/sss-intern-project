package com.internproject.orderservice.config;

import com.internproject.orderservice.mapper.CartMapstruct;
import com.internproject.orderservice.mapper.OrderMapstruct;
import com.internproject.orderservice.mapper.OrderMapstructImpl;
import com.internproject.orderservice.mapper.CartMapstructImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Bean
    public CartMapstruct cartMapstruct() {
        return new CartMapstructImpl();
    }

    @Bean
    public OrderMapstruct orderMapstruct() {
        return new OrderMapstructImpl();
    }
}
