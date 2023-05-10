package com.internproject.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateRequest {
    private String fullName;
    private int provinceId;
    private int districtId;
    private int wardId;
    private String phone;
    private boolean gender;
    private Date dob;
    private String avatar;
}
