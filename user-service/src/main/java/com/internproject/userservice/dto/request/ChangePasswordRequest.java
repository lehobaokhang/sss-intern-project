package com.internproject.userservice.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
