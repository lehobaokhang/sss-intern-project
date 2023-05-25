package com.internproject.productservice.fallback;

import com.internproject.productservice.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements IUserService {
    @Override
    public String getUserFullName(String id) {
        new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        return null;
    }
}
