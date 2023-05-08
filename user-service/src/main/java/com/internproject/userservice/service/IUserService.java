package com.internproject.userservice.service;

import com.internproject.userservice.dto.RegisterRequest;
import com.internproject.userservice.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    public User register(RegisterRequest registerRequest);
}
