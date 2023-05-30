package com.internproject.userservice.controller;

import com.internproject.userservice.dto.RoleDTO;
import com.internproject.userservice.dto.request.*;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.jwt.JwtUtils;
import com.internproject.userservice.service.MessageProducer;
import com.internproject.userservice.service.RoleService;
import com.internproject.userservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Api(value = "Auth", description = "Authenticate and authorization user")
public class AuthController {
    private UserService userService;
    private RoleService roleService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private MessageProducer messageProducer;

    @Autowired
    public AuthController(UserService userService,
                          RoleService roleService,
                          AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils,
                          MessageProducer messageProducer) {
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.messageProducer = messageProducer;
    }

    @PostMapping("/role")
    @ApiOperation(value = "Create new role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addNewRole(@RequestBody String requestBody) {
        roleService.addNewRole(requestBody);
        return ResponseEntity.ok("Role is created successfully");
    }

    @DeleteMapping("/role/{id}")
    @ApiOperation(value = "Delete role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteRole(@PathVariable("id") String id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Delete role successful");
    }

    @PostMapping("/register")
    @ApiOperation(value = "Create new account")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        Optional<User> user = userService.register(registerRequest);
        return (user.isPresent()) ?
                ResponseEntity.ok("User is registered successfully") :
                ResponseEntity.badRequest().body("User can not registered");
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login with username and password")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok(jwt);
    }

    @PutMapping("/add-user-role/{id}")
    @ApiOperation(value = "Admin will add new role for user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addRoleForUser(@PathVariable("id") String userId,
                                                 @RequestBody RoleDTO roleDTO) {
        userService.addRoleForUser(userId, roleDTO);
        return ResponseEntity.ok("Add role for user successful");
    }

    @PostMapping("/change-password")
    @ApiOperation(value = "Change password base on old password and userId")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String id = getIdFromToken(authorizationHeader);
        SendMailRequest request = userService.changePassword(changePasswordRequest, id);
        messageProducer.send(request);
        return ResponseEntity.ok("Password has ben changed");
    }

    @PostMapping("/reset-password")
    @ApiOperation(value = "Password will randomize by 10 character from ascii and send this password to user's email")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        SendMailRequest sendMailRequest = userService.resetPassword(request);
        messageProducer.send(sendMailRequest);
        return ResponseEntity.ok("New password has been send to your email");
    }

    private String getIdFromToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);
        return id;
    }

}
