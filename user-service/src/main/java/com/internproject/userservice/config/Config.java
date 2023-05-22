package com.internproject.userservice.config;

import com.internproject.userservice.mapper.UserMapstruct;
import com.internproject.userservice.mapper.UserMapstructImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    @Bean
    public UserMapstruct userMapstruct() {
        return new UserMapstructImpl();
    }
}
