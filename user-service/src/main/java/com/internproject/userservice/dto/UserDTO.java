package com.internproject.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.internproject.userservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private Timestamp createdAt;
    private UserDetailDTO userDetailDTO;
    @JsonIgnore
    private Set<Role> roles;
}
