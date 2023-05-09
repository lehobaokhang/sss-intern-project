package com.internproject.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoder {
    @Bean
    public org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}
