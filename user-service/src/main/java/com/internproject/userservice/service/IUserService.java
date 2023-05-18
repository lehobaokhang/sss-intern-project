package com.internproject.userservice.service;

import com.internproject.userservice.dto.*;
import com.internproject.userservice.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends UserDetailsService {
    public Optional<User> register(RegisterRequest registerRequest);

    public MeDTO getMe(String username);

    public UserCredential getUserById(String id);

    public boolean deleteUser(String id);

    public User updateUser(String id, UserUpdateRequest userUpdateRequest);

    boolean changePassword(ChangePasswordRequest changePasswordRequest, String id);

    String getUserFullName(String id);
}
