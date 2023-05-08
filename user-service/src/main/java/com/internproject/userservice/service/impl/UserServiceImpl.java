package com.internproject.userservice.service.impl;

import com.internproject.userservice.dto.RegisterRequest;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.entity.UserDetail;
import com.internproject.userservice.exception.EmailExistException;
import com.internproject.userservice.exception.UsernameExistException;
import com.internproject.userservice.repository.IUserRepository;
import com.internproject.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.internproject.userservice.enumeration.Role.ROLE_USER;

@Service
@Qualifier("userDetailsService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }

    @Override
    public User register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UsernameExistException("Username already exists");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailExistException("Email already exists");
        }

        User newUser = new User();
        UserDetail newUserDetail = new UserDetail();

        newUserDetail.setFullName(registerRequest.getFullName());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        newUser.setEmail(registerRequest.getEmail());
        newUser.setRole(ROLE_USER.name());
        newUser.setUserDetail(newUserDetail);

        userRepository.save(newUser);

        return newUser;
    }
}
