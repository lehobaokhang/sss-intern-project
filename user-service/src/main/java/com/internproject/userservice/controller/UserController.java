package com.internproject.userservice.controller;

import com.internproject.userservice.dto.UserDTO;
import com.internproject.userservice.dto.UserDetailDTO;
import com.internproject.userservice.jwt.JwtUtils;
import com.internproject.userservice.service.UserFacade;
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
    private UserFacade userFacade;
    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping
    @ApiOperation(value = "Get Information Of Current User By Bearer Token In Authorization Header")
    public ResponseEntity<UserDTO> getMe(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        UserDTO userDTO = userFacade.getMe(authorizationHeader);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Information Of User By Id")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        UserDTO userDTO = userFacade.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping
    @ApiOperation(value ="Delete User By Bearer Token In Authorization Header")
    public ResponseEntity<String> deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        userFacade.deleteUser(authorizationHeader);
        return ResponseEntity.ok("Delete account success");
    }

    @PutMapping
    @ApiOperation(value = "Update User Information By Bearer Token Authorization Header")
    public ResponseEntity<String> updateUser(@RequestBody UserDetailDTO userDetailDTO,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        userFacade.updateUser(userDetailDTO, authorizationHeader);
        return ResponseEntity.ok("Update user's information successful");
    }
}
