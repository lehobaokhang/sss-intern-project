package com.internproject.userservice.service;

import com.internproject.userservice.config.UserDetailsImpl;
import com.internproject.userservice.dto.*;
import com.internproject.userservice.dto.request.ChangePasswordRequest;
import com.internproject.userservice.dto.request.RegisterRequest;
import com.internproject.userservice.entity.Role;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.entity.UserDetail;
import com.internproject.userservice.exception.*;
import com.internproject.userservice.jwt.JwtUtils;
import com.internproject.userservice.mapper.UserMapstruct;
import com.internproject.userservice.repository.IRoleRepository;
import com.internproject.userservice.repository.IUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private IUserRepository userRepository;
    private IRoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtUtils jwtUtils;
    private UserMapstruct userMapstruct;
    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    public UserService(IUserRepository userRepository,
                       IRoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       JwtUtils jwtUtils,
                       UserMapstruct userMapstruct) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
        this.userMapstruct = userMapstruct;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        return userDetails;

    }

    public Optional<User> register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            logger.error("Username '" + registerRequest.getUsername() + "' already exists");
            throw new UsernameExistException("Username '" + registerRequest.getUsername() + "' already exists");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            logger.error("Email '" + registerRequest.getEmail() + "' already exists");
            throw new EmailExistException("Email '" + registerRequest.getEmail() + "' already exists");
        }

        Optional<Role> roleOptional = roleRepository.findByRoleName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        if (!roleOptional.isPresent()) {
            throw new RoleNotFoundException("Role not found");
        }

        User newUser = new User();
        UserDetail newUserDetail = new UserDetail();
        roles.add(roleOptional.get());

        newUserDetail.setFullName(registerRequest.getFullName());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        newUser.setEmail(registerRequest.getEmail());
        newUser.setUserDetail(newUserDetail);
        newUser.setRoles(roles);
        userRepository.save(newUser);

        return Optional.of(newUser);
    }

    public UserDTO getMe(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return userMapstruct.toUserDTO(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public UserDTO getUserById(String id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return userMapstruct.toUserDTO(user);
        } else {
            throw new UserNotFoundException("Can not find any user with id: " + id);
        }
    }

    public boolean deleteUser(String userId, String idFromToken) {
        if (!checkUserId(userId, idFromToken)) {
            throw new DoOnOtherUserInformationException("Can not delete another user's account");
        }
        userRepository.deleteById(userId);
        return true;
    }

    public User updateUser(String userId, String idFromToken, UserDetailDTO userDetailDTO) {
        if (!checkUserId(userId, idFromToken)) {
            throw new DoOnOtherUserInformationException("Cannot change other users' information");
        }
        UserDetail newUserDetail = userMapstruct.toUserDetail(userDetailDTO);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            newUserDetail.setId(user.getUserDetail().getId());
            user.setUserDetail(newUserDetail);
            userRepository.save(user);
            return user;
        } else {
            throw new UserNotFoundException("Can not find user with id: " + userId + " for update");
        }
    }

    public void addRoleForUser(String id, RoleDTO roleDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<Role> roleOptional = roleRepository.findByRoleName(roleDTO.getRoleName());
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("Can not find any user with id: " + id);
        }
        if (!roleOptional.isPresent()) {
            throw new RoleNotFoundException("Can not find any role with role's name: " + roleDTO.getRoleName());
        }
        User user = userOptional.get();
        Role role = roleOptional.get();
        user.addRole(role);
        userRepository.save(user);
    }

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

    public String getUserFullName(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.isPresent()
                ? userOptional.get().getUserDetail().getFullName()
                : null;
    }

    public boolean checkUserId(String id, String idFromToken) {
        if (id.equals(idFromToken)) {
            return true;
        }
        return false;
    }
}