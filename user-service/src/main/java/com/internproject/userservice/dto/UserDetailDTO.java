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
    private int provinceID;
    private int districtID;
    private int wardId;
    private String phone;
    private String gender;
    private Date dob;
}
