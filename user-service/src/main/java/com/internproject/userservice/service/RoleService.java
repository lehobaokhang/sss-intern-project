package com.internproject.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internproject.userservice.entity.Role;
import com.internproject.userservice.exception.RoleNotFoundException;
import com.internproject.userservice.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private IRoleRepository roleRepository;

    public void addNewRole(String request) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> bodyMap = null;
        try {
            bodyMap = objectMapper.readValue(request, new TypeReference<Map<String, String>>(){});
        } catch (JsonProcessingException e) {

        }
        Role role = new Role();
        role.setRoleName(bodyMap.get("roleName"));
        roleRepository.save(role);
    }

    public void deleteRole(String id) {
        roleRepository.deleteById(id);
    }
    public Role findByRoleName(String roleName) {
        Optional<Role> roleOptional = roleRepository.findByRoleName(roleName);
        if (!roleOptional.isPresent()) {
            throw new RoleNotFoundException("Role not found");
        }
        return roleOptional.get();
    }
}
