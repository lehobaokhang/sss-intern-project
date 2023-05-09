package com.internproject.userservice.dto;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotNull
    private String username;

    @NotNull
    private String password;
}
