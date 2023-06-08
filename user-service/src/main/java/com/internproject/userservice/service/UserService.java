package com.internproject.userservice.service;

import com.internproject.userservice.config.UserDetailsImpl;
import com.internproject.userservice.dto.*;
import com.internproject.userservice.dto.request.ChangePasswordRequest;
import com.internproject.userservice.dto.request.RegisterRequest;
import com.internproject.userservice.dto.request.ResetPasswordRequest;
import com.internproject.userservice.dto.request.SendMailRequest;
import com.internproject.userservice.entity.Role;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.entity.UserDetail;
import com.internproject.userservice.exception.*;
import com.internproject.userservice.mapper.UserMapstruct;
import com.internproject.userservice.repository.IUserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private IUserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserMapstruct userMapstruct;

    @Autowired
    public UserService(IUserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserMapstruct userMapstruct) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapstruct = userMapstruct;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return UserDetailsImpl.build(user);
    }

    public boolean existsByUserName(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User register(RegisterRequest registerRequest, Role role) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User newUser = new User();
        UserDetail newUserDetail = new UserDetail();

        newUserDetail.setFullName(registerRequest.getFullName());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        newUser.setEmail(registerRequest.getEmail());
        newUser.setUserDetail(newUserDetail);
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }

    public UserDTO getMe(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapstruct.toUserDTO(user);
    }

    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Can not find any user with id: " + id));
        UserDTO userDTO = userMapstruct.toUserDTO(user);
        userDTO.setUsername(null);
        userDTO.setCreatedAt(null);
        userDTO.getUserDetailDTO().setPhone(null);
        userDTO.getUserDetailDTO().setDob(null);
        return userDTO;
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public User updateUser(UserDetailDTO userDetailDTO, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Can not find user with id: " + userId + " for update"));
        UserDetail userDetail = userMapstruct.toUserDetail(userDetailDTO);
        userDetail.setId(user.getUserDetail().getId());
        user.setUserDetail(userDetail);
        userRepository.save(user);
        return user;
    }

    public void addRoleForUser(String id, Role role) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Can not find any user with id: " + id));
        user.addRole(role);
        userRepository.save(user);
    }

    public SendMailRequest changePassword(ChangePasswordRequest changePasswordRequest, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Can not find any user with id :" + id));
        if (!user.getId().equals(id)) {
            throw new DoOnOtherUserInformationException("Can not chang information of another user");
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())) {
            throw new ChangePasswordException("New password and confirm password does not match");
        }
        if (!bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new ChangePasswordException("Old password does not valid");
        }
        String newPassword = bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword());
        user.setPassword(newPassword);
        userRepository.save(user);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return new SendMailRequest(user.getEmail(),
                "CHANGE PASSWORD NOTIFICATION",
                user.getUserDetail().getFullName(),
                String.format("Your password has been change at: %s", LocalDateTime.now().format(formatter)));
    }

    public SendMailRequest resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format("Can not find any user with username '%s' or email '%s'", request.getUsername(), request.getEmail())));
        String newPassword = generatePassword();
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
        return new SendMailRequest(
                user.getEmail(),
                "NEW PASSWORD",
                user.getUserDetail().getFullName(),
                String.format("New password of your account is: %s%nPlease change your password as soon as you receive this email", newPassword)
        );
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
