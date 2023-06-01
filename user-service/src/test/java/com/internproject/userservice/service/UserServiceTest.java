package com.internproject.userservice.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.internproject.userservice.dto.request.RegisterRequest;
import com.internproject.userservice.entity.Role;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.jwt.JwtUtils;
import com.internproject.userservice.mapper.UserMapstruct;
import com.internproject.userservice.repository.IRoleRepository;
import com.internproject.userservice.repository.IUserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private MessageProducer messageProducer;
//
//    @Mock
//    private JwtUtils jwtUtils;
//
//    @Mock
//    private ShipService shipService;

    @Mock
    private IRoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

//    @InjectMocks
//    private UserFacade userFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void register_WhenUsernameAndEmailDoNotExist_ShouldRegisterUser() {
        // Arrange
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setUsername("khang.lehobao");
//        registerRequest.setEmail("khang.lehobao@yopmail.com");
//        registerRequest.setPassword("123456");
//        registerRequest.setFullName("KHANG Le Ho Bao");
        Role role = new Role();
        role.setRoleName("ROLE_USER");
        when(roleService.findByRoleName("ROLE_USER")).thenReturn(role);
//        when(userService.existsByUserName(eq("khang.lehobao"))).thenReturn(false);
//        when(userService.existsByEmail(eq("khang.lehobao@yopmail.com"))).thenReturn(false);
//        User savedUser = new User();
//        when(userService.register(eq(registerRequest), eq(role))).thenReturn(savedUser);
//
//        // Act
//        User result = userFacade.register(registerRequest);
//
//        // Assert
//        assertNotNull(result);
//        assertNotNull(result.getId());
//        assertEquals(registerRequest.getUsername(), result.getUsername());
//        assertEquals(registerRequest.getEmail(), result.getEmail());
//        verify(userService).register(eq(registerRequest), eq(role));
    }
}
