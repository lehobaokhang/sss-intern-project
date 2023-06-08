package com.internproject.userservice.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String fullName;
}
