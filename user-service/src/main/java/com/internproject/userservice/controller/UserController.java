package com.internproject.userservice.controller;

import com.internproject.userservice.dto.UserDTO;
import com.internproject.userservice.dto.UserDetailDTO;
import com.internproject.userservice.jwt.JwtUtils;
import com.internproject.userservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "Auth", description = "User Controller")
public class UserController {
    private JwtUtils jwtUtils;
    private UserService userService;
    @Autowired
    public UserController(JwtUtils jwtUtils,
                          UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "Get Information Of Current User By Bearer Token In Authorization Header")
    public ResponseEntity<UserDTO> getMe(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String userId = getIdFromToken(authorizationHeader);
        return ResponseEntity.ok(userService.getMe(userId));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Information Of User By Id")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value ="Delete User By Bearer Token In Authorization Header")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String userId,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String idFromToken = getIdFromToken(authorizationHeader);
        userService.deleteUser(userId, idFromToken);
        return ResponseEntity.ok("Delete account success");
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update User Information By Bearer Token Authorization Header")
    public ResponseEntity<String> updateUser(@PathVariable("id") String userId,
                                             @RequestBody UserDetailDTO userDetailDTO,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String idFromToken = getIdFromToken(authorizationHeader);
        userService.updateUser(userId, idFromToken, userDetailDTO);
        return ResponseEntity.ok("Update user's information successful");
    }

    @GetMapping("/get-fullname/{id}")
    @ApiOperation(value = "Get fullname of user by user id")
    public ResponseEntity<String> getUserFullName(@PathVariable("id") String id) {
        String fullName = userService.getUserFullName(id);
        return ResponseEntity.ok(fullName);
    }

    private String getIdFromToken(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);
        return id;
    }
}
