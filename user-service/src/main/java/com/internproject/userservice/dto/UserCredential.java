package com.internproject.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredential {
    private String id;
    private String fullName;
    private boolean gender;
    private Date dob;
    private Timestamp created_at;
}
