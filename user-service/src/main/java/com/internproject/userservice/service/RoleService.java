package com.internproject.userservice.service;

import com.internproject.userservice.dto.RoleDTO;
import com.internproject.userservice.entity.Role;
import com.internproject.userservice.exception.RoleNotFoundException;
import com.internproject.userservice.mapper.RoleMapstruct;
import com.internproject.userservice.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private IRoleRepository roleRepository;
    private RoleMapstruct roleMapstruct;

    @Autowired
    public RoleService(IRoleRepository roleRepository,
                       RoleMapstruct roleMapstruct) {
        this.roleRepository = roleRepository;
        this.roleMapstruct = roleMapstruct;
    }

    public void addNewRole(RoleDTO roleDTO) {
        Role role = roleMapstruct.toRole(roleDTO);
        roleRepository.save(role);
    }

    public void deleteRole(String id) {
        roleRepository.deleteById(id);
    }
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() -> new RoleNotFoundException("Role not found"));
    }
}
