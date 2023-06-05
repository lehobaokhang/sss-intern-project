package com.internproject.productservice.fallback;

import com.internproject.productservice.service.UserService;

public class UserServiceFallback implements UserService {
    @Override
    public String getUserFullName(String id, String authorizationHeader) {
        return null;
    }
}
