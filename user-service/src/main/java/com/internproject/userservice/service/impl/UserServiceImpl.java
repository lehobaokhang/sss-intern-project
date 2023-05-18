package com.internproject.userservice.service.impl;

import com.internproject.userservice.config.UserDetailsImpl;
import com.internproject.userservice.dto.*;
import com.internproject.userservice.entity.Role;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.entity.UserDetail;
import com.internproject.userservice.exception.EmailExistException;
import com.internproject.userservice.exception.UsernameExistException;
import com.internproject.userservice.jwt.JwtUtils;
import com.internproject.userservice.mapper.UserMapper;
import com.internproject.userservice.repository.IRoleRepository;
import com.internproject.userservice.repository.IUserRepository;
import com.internproject.userservice.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private JwtUtils jwtUtils;

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        return userDetails;

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

    @Override
    public MeDTO getMe(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new MeDTO(user.getId(), user.getEmail(), user.getUserDetail().getFullName(), user.getRoles().iterator().next().getRoleName());
        }

        return null;
    }

    @Override
    public UserCredential getUserById(String id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserCredential(user.getId(),
                    user.getUserDetail().getFullName(),
                    user.getUserDetail().isGender(),
                    user.getUserDetail().getDob(),
                    user.getCreated_at());
        }

        return null;
    }

    @Override
    public boolean deleteUser(String id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public User updateUser(String id, UserUpdateRequest userUpdateRequest) {
        UserDetail userDetail = UserMapper.getInstance().toUserDetail(userUpdateRequest);

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            userDetail.setId(user.getUserDetail().getId());
            user.setUserDetail(userDetail);
            userRepository.save(user);

            return user;
        }

        return null;
    }

    @Override
    public boolean changePassword(ChangePasswordRequest changePasswordRequest, String id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            return false;
        }

        User user = userOptional.get();
        if (!user.getId().equals(id)) {
            return false;
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())) {
            return false;
        }

        if (!bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            return false;
        }

        String newPassword = bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword());
        user.setPassword(newPassword);
        userRepository.save(user);

        return true;
    }

    @Override
    public String getUserFullName(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.isPresent()
                ? userOptional.get().getUserDetail().getFullName()
                : null;
    }
}
