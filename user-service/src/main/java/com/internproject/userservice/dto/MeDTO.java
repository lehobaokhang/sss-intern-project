package com.internproject.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class MeDTO {
    private String id;
    private String email;
    private String fullName;
    private String role;
}
