package com.internproject.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.internproject.userservice.config.UserDetailsImpl;
import com.internproject.userservice.dto.LoginRequest;
import com.internproject.userservice.dto.LoginResponse;
import com.internproject.userservice.dto.RegisterRequest;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.config.AuthEntryPointJwt;
import com.internproject.userservice.jwt.JwtUtils;
import com.internproject.userservice.service.IRoleService;
import com.internproject.userservice.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    private static final Logger logger = LogManager.getLogger(AuthEntryPointJwt.class);

    // Manage role method
    @PostMapping("/role")
    @ApiOperation(value = "Create new role")
    public ResponseEntity<String> addNewRole(@RequestBody String requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> bodyMap = null;
        try {
            bodyMap = objectMapper.readValue(requestBody, new TypeReference<Map<String, String>>(){});
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("Bad Request");
        }

        roleService.addNewRole(bodyMap.get("role"));
        logger.info("Add role to database");
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new LoginResponse(jwt,
                userDetails.getId(),
                Iterables.get(userDetails.getAuthorities(), 0).toString()));
    }

}
