package com.internproject.userservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RoleDTO {
    private String id;
    private String roleName;
}
