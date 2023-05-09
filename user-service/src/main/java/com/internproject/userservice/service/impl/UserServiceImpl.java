package com.internproject.userservice.service.impl;

import com.internproject.userservice.dto.RegisterRequest;
import com.internproject.userservice.entity.Role;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.entity.UserDetail;
import com.internproject.userservice.exception.EmailExistException;
import com.internproject.userservice.exception.UsernameExistException;
import com.internproject.userservice.repository.IRoleRepository;
import com.internproject.userservice.repository.IUserRepository;
import com.internproject.userservice.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Qualifier("userDetailsService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }

    @Override
    public Optional<User> register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UsernameExistException("Username already exists");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailExistException("Email already exists");
        }

        Optional<Role> role = roleRepository.findByRoleName("ROLE_USER");
        Set<Role> roles = new HashSet<>();

        if (!role.isPresent()) {
            logger.error("Role not found");
            return null;
        }

        User newUser = new User();
        UserDetail newUserDetail = new UserDetail();
        roles.add(role.get());

        newUserDetail.setFullName(registerRequest.getFullName());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        newUser.setEmail(registerRequest.getEmail());
        newUser.setUserDetail(newUserDetail);
        newUser.setRoles(roles);

        userRepository.save(newUser);

        return Optional.of(newUser);
    }
}
