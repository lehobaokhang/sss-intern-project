package com.internproject.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internproject.userservice.dto.RoleDTO;
import com.internproject.userservice.dto.request.ChangePasswordRequest;
import com.internproject.userservice.dto.request.LoginRequest;
import com.internproject.userservice.dto.request.RegisterRequest;
import com.internproject.userservice.dto.request.SendMailRequest;
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

import java.util.Map;
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

    // Manage role method
    @PostMapping("/role")
    @ApiOperation(value = "Create new role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addNewRole(@RequestBody String requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> bodyMap = null;
        try {
            bodyMap = objectMapper.readValue(requestBody, new TypeReference<Map<String, String>>(){});
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Bad Request");
        }

        roleService.addNewRole(bodyMap.get("role"));
        return ResponseEntity.ok("Role is created successfully");
    }

    //User Endpoints APIs
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


    //need to send email for confirm
    @PostMapping("/change-password")
    @ApiOperation(value = "Change password base on old password and userId")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String id = getIdFromToken(authorizationHeader);
        SendMailRequest request = userService.changePassword(changePasswordRequest, id);
        messageProducer.send(request);
        return ResponseEntity.ok("Password has ben changed");
    }

    private String getIdFromToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);
        return id;
    }

}
