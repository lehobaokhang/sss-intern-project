package com.internproject.userservice.service;

import com.internproject.userservice.dto.MeDTO;
import com.internproject.userservice.dto.RegisterRequest;
import com.internproject.userservice.dto.UserCredential;
import com.internproject.userservice.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends UserDetailsService {
    public Optional<User> register(RegisterRequest registerRequest);

    public MeDTO getMe(String username);

    public UserCredential getUserById(String id);
}
