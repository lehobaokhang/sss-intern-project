package com.internproject.userservice.service;

import com.internproject.userservice.dto.*;
import com.internproject.userservice.dto.request.ChangePasswordRequest;
import com.internproject.userservice.dto.request.RegisterRequest;
import com.internproject.userservice.dto.request.UserUpdateRequest;
import com.internproject.userservice.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends UserDetailsService {
    Optional<User> register(RegisterRequest registerRequest);

    MeDTO getMe(String username);

    UserCredential getUserById(String id);

    boolean deleteUser(String id);

    User updateUser(String id, UserUpdateRequest userUpdateRequest);

    boolean changePassword(ChangePasswordRequest changePasswordRequest, String id);

    String getUserFullName(String id);
}
