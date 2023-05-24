package com.internproject.userservice.config;

import com.internproject.userservice.entity.Role;
import org.springframework.batch.item.ItemProcessor;

public class RoleProcessor implements ItemProcessor<Role, Role> {
    @Override
    public Role process(Role role) throws Exception {
        return role;
    }
}
