package com.internproject.userservice.controller;

import com.internproject.userservice.dto.MeDTO;
import com.internproject.userservice.dto.UserCredential;
import com.internproject.userservice.jwt.JwtUtils;
import com.internproject.userservice.service.IUserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<MeDTO> getMe(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String username = jwtUtils.getUsernameFromJwtToken(jwt);

        MeDTO meDTO = userService.getMe(username);

        return meDTO != null
                ? ResponseEntity.ok(meDTO)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCredential> getUserById(@PathVariable String id) {
        UserCredential userCredential = userService.getUserById(id);
        return userCredential != null
                ? ResponseEntity.ok(userCredential)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String jwt = authorizationHeader.substring(7, authorizationHeader.length());
        String id = jwtUtils.getIdFromJwtToken(jwt);

        return userService.deleteUser(id)
                ? ResponseEntity.ok("Delete account success")
                : ResponseEntity.internalServerError().build();
    }
}
