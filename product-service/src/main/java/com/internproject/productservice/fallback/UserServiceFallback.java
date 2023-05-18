package com.internproject.productservice.fallback;

import com.internproject.productservice.service.IUserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements IUserService {
    @Override
    public String getUserFullName(String id) {
        System.out.println(1);
        return null;
    }
}
