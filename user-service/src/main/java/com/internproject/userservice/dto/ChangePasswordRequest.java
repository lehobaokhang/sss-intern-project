package com.internproject.userservice.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotNull
    private String oldPassword;

    @NotNull
    private String newPassword;

    @NotNull
    private String confirmNewPassword;
}
