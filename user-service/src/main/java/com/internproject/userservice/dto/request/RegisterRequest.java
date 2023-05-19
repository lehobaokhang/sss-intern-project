package com.internproject.userservice.dto.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RegisterRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    private String fullName;
}
