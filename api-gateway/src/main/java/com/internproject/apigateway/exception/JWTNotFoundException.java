package com.internproject.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JWTNotFoundException extends RuntimeException{
    public JWTNotFoundException(String message) {
        super(message);
    }
}
