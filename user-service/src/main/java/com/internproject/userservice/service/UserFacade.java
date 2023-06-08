package com.internproject.userservice.service;

import com.internproject.userservice.dto.RoleDTO;
import com.internproject.userservice.dto.UserDTO;
import com.internproject.userservice.dto.UserDetailDTO;
import com.internproject.userservice.dto.request.*;
import com.internproject.userservice.entity.Role;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.exception.AddressException;
import com.internproject.userservice.exception.EmailExistException;
import com.internproject.userservice.exception.UsernameExistException;
import com.internproject.userservice.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserFacade {
    private UserService userService;
    private RoleService roleService;
    private MessageProducer messageProducer;
    private JwtUtils jwtUtils;
    private ShipService shipService;

    @Autowired
    public UserFacade(UserService userService,
                      RoleService roleService,
                      MessageProducer messageProducer,
                      JwtUtils jwtUtils,
                      ShipService shipService) {
        this.userService = userService;
        this.roleService = roleService;
        this.messageProducer = messageProducer;
        this.jwtUtils = jwtUtils;
        this.shipService = shipService;
    }

    private String getIdFromToken(String authorizationHeader) {
        return jwtUtils.getIdFromJwtToken(authorizationHeader);
    }

    public String generateToken(Authentication authentication) {
        return jwtUtils.generateJwtToken(authentication);
    }

    public User register(RegisterRequest registerRequest) {
        if (userService.existsByUserName(registerRequest.getUsername())) {
            throw new UsernameExistException("Username '" + registerRequest.getUsername() + "' already exists");
        }
        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new EmailExistException("Email '" + registerRequest.getEmail() + "' already exists");
        }
        Role role = roleService.findByRoleName("ROLE_USER");

        return userService.register(registerRequest, role);
    }

    public void addRoleForUser(String id, RoleDTO roleDTO) {
        Role role = roleService.findByRoleName(roleDTO.getRoleName());
        userService.addRoleForUser(id, role);
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest,
                               String authorizationHeader) {
        String id = getIdFromToken(authorizationHeader);
        SendMailRequest mailRequest = userService.changePassword(changePasswordRequest, id);
        messageProducer.send(mailRequest);
    }

    public void resetPassword(ResetPasswordRequest request) {
        SendMailRequest mailRequest = userService.resetPassword(request);
        messageProducer.send(mailRequest);
    }

    public UserDTO getMe(String authorizationHeader) {
        String id = getIdFromToken(authorizationHeader);
        return userService.getMe(id);
    }

    public UserDTO getUserById(String id) {
        return userService.getUserById(id);
    }

    public void deleteUser(String authorizationHeader) {
        String id = getIdFromToken(authorizationHeader);
        userService.deleteUser(id);
    }

    public void updateUser(UserDetailDTO userDetailDTO,
                           String authorizationHeader) {
        String id = getIdFromToken(authorizationHeader);
        boolean validAddress = shipService.isDistrictValid(userDetailDTO.getDistrictID(), userDetailDTO.getProvinceID(), authorizationHeader);
        if (!validAddress) {
            throw new AddressException("Address invalid");
        }
        userService.updateUser(userDetailDTO, id);
    }

    public int getDistrict(String id) {
        UserDTO user = userService.getUserById(id);
        return user.getUserDetailDTO().getDistrictID();
    }
}
