package com.internproject.userservice.service;

import com.internproject.userservice.entity.Role;
import com.internproject.userservice.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private IRoleRepository roleRepository;

    public void addNewRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);

        roleRepository.save(role);
    }
}
