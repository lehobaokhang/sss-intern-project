package com.internproject.userservice.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class UserCsv {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String phone;
    private String dob;
}
