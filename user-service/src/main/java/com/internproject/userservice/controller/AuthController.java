package com.internproject.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internproject.userservice.dto.RegisterRequest;
import com.internproject.userservice.service.IRoleService;
import com.internproject.userservice.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Api(value = "Auth", description = "Authenticate and authorization user")
public class AuthController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    // Manage role method
    @PostMapping("/role")
    @ApiOperation(value = "Create new role")
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

    @PostMapping("/register")
    @ApiOperation(value = "Create new account")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return ResponseEntity.ok("User is registered successfully");
    }



}
