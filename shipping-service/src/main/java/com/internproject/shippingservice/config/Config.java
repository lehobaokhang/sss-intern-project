package com.internproject.shippingservice.config;

import com.internproject.shippingservice.mapper.RatingMapstruct;
import com.internproject.shippingservice.mapper.RatingMapstructImpl;
import com.internproject.shippingservice.mapper.ShipMapstruct;
import com.internproject.shippingservice.mapper.ShipMapstructImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Bean
    public ShipMapstruct shipMapstruct() {
        return new ShipMapstructImpl();
    }

    @Bean
    public RatingMapstruct ratingMapstruct() {
        return new RatingMapstructImpl();
    }
}
