package com.internproject.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendMailRequest {
    private String to;
    private String subject;
    private String fullName;
    private String message;
}
