package com.internproject.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetailDTO {
    private String fullName;
    private int provinceId;
    private int districtID;
    private int wardId;
    private String phone;
    private boolean gender;
    private Date dob;
}
