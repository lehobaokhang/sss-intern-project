package com.internproject.userservice.service;

import com.internproject.userservice.entity.Role;
import com.internproject.userservice.repository.IRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    @Mock
    private IRoleRepository roleRepository;
    @InjectMocks
    private RoleService roleService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByRoleName() {
        String roleName = "ROLE_USER";
        Role role = new Role();
        role.setRoleName(roleName);

        Role roles = new Role();
        roles.setRoleName("ROLE_USER");

        when(roleRepository.findByRoleName("ROLE_USER")).thenReturn(Optional.of(roles));



    }
}
