package com.internproject.userservice.service.impl;

import com.internproject.userservice.entity.Role;
import com.internproject.userservice.repository.IRoleRepository;
import com.internproject.userservice.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public void addNewRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);

        roleRepository.save(role);
    }
}
