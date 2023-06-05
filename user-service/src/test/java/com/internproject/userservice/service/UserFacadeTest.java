package com.internproject.userservice.service;

import com.internproject.userservice.dto.RoleDTO;
import com.internproject.userservice.dto.UserDTO;
import com.internproject.userservice.dto.UserDetailDTO;
import com.internproject.userservice.dto.request.ChangePasswordRequest;
import com.internproject.userservice.dto.request.RegisterRequest;
import com.internproject.userservice.dto.request.ResetPasswordRequest;
import com.internproject.userservice.dto.request.SendMailRequest;
import com.internproject.userservice.entity.Role;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.exception.AddressException;
import com.internproject.userservice.exception.EmailExistException;
import com.internproject.userservice.exception.UsernameExistException;
import com.internproject.userservice.jwt.JwtUtils;
import com.internproject.userservice.mapper.UserMapstruct;
import com.internproject.userservice.repository.IRoleRepository;
import com.internproject.userservice.repository.IUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserFacadeTest {
    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private MessageProducer messageProducer;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private ShipService shipService;

    @InjectMocks
    private UserFacade userFacade;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void register_WhenUsernameAndEmailDoNotExist_ShouldRegisterUser() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("khanglehobao");
        registerRequest.setEmail("khanglehobao@example.com");

        when(userService.existsByUserName(registerRequest.getUsername())).thenReturn(false);
        when(userService.existsByEmail(registerRequest.getEmail())).thenReturn(false);

        Role role = new Role();
        role.setRoleName("ROLE_USER");
        when(roleService.findByRoleName("ROLE_USER")).thenReturn(role);

        User savedUser = new User();
        when(userService.register(registerRequest, role)).thenReturn(savedUser);

        User result = userFacade.register(registerRequest);

        verify(userService).existsByUserName(registerRequest.getUsername());
        verify(userService).existsByEmail(registerRequest.getEmail());
        verify(roleService).findByRoleName("ROLE_USER");
        verify(userService).register(registerRequest, role);

        assertEquals(savedUser, result);
    }

    @Test
    public void register_WhenUsernameExists_ShouldThrowUsernameExistException() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("khang.le");
        registerRequest.setEmail("khang.le1@example.com");
        when(userService.existsByUserName(registerRequest.getUsername())).thenReturn(true);

        assertThrows(UsernameExistException.class, () -> userFacade.register(registerRequest));
    }

    @Test
    public void register_WhenEmailExists_ShouldThrowEmailExistException() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("khang.le123");
        registerRequest.setEmail("khang.le@example.com");
        when(userService.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        assertThrows(EmailExistException.class, () -> userFacade.register(registerRequest));
    }

    @Test
    public void addRoleForUser_ShouldAddRoleForUser() {
        String userId = "userId";
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleName("ROLE_ADMIN");

        Role role = new Role();
        role.setRoleName("ROLE_ADMIN");

        when(roleService.findByRoleName("ROLE_ADMIN")).thenReturn(role);

        userFacade.addRoleForUser(userId, roleDTO);

        verify(roleService).findByRoleName("ROLE_ADMIN");
        verify(userService).addRoleForUser(userId, role);
    }

    @Test
    public void changePassword_ShouldChangePassword() {
        // Mock the necessary dependencies
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("oldPassword");
        changePasswordRequest.setNewPassword("newPassword");
        changePasswordRequest.setConfirmNewPassword("newPassword");

        String authorizationHeader = "< Token >";
        String userId = "userId";

        SendMailRequest mailRequest = new SendMailRequest();
        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);
        when(userService.changePassword(changePasswordRequest, userId)).thenReturn(mailRequest);
        userFacade.changePassword(changePasswordRequest, authorizationHeader);

        verify(jwtUtils).getIdFromJwtToken(authorizationHeader);
        verify(userService).changePassword(changePasswordRequest, userId);
        verify(messageProducer).send(mailRequest);
    }

    @Test
    public void resetPassword_ResetByUsername_ShouldResetPassword() {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setUsername("username");
        SendMailRequest sendMailRequest = new SendMailRequest();

        when(userService.resetPassword(resetPasswordRequest)).thenReturn(sendMailRequest);
        userFacade.resetPassword(resetPasswordRequest);

        verify(userService).resetPassword(resetPasswordRequest);
        verify(messageProducer).send(sendMailRequest);
    }

    @Test
    public void resetPassword_ResetByEmail_ShouldResetPassword() {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail("email");
        SendMailRequest sendMailRequest = new SendMailRequest();

        when(userService.resetPassword(resetPasswordRequest)).thenReturn(sendMailRequest);
        userFacade.resetPassword(resetPasswordRequest);

        verify(userService).resetPassword(resetPasswordRequest);
        verify(messageProducer).send(sendMailRequest);
    }

    @Test
    public void getMe_ShouldReturnUserDTO() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";

        UserDTO userDTO = new UserDTO();
        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);
        when(userService.getMe(userId)).thenReturn(userDTO);

        UserDTO resultUserDTO = userFacade.getMe(authorizationHeader);

        verify(jwtUtils).getIdFromJwtToken(authorizationHeader);
        verify(userService).getMe(userId);

        assertEquals(userDTO, resultUserDTO);
    }

    @Test
    public void getUserById_ShouldReturnUserDTO() {
        String userId = "user id";
        UserDTO userDTO = new UserDTO();
        when(userService.getUserById(userId)).thenReturn(userDTO);

        UserDTO result = userFacade.getUserById(userId);
        verify(userService).getUserById(userId);

        assertEquals(userDTO, result);
    }

    @Test
    public void deleteUser_ShouldDeleteUser() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        userFacade.deleteUser(authorizationHeader);

        verify(jwtUtils).getIdFromJwtToken(authorizationHeader);
        verify(userService).deleteUser(userId);
    }

    @Test
    public void updateUser_ShouldUpdateUser() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setFullName("fullName");
        userDetailDTO.setProvinceID(0);
        userDetailDTO.setDistrictID(0);
        userDetailDTO.setWardId(0);
        userDetailDTO.setPhone("phone");
        userDetailDTO.setGender("gender");
        userDetailDTO.setDob(new Date());
        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        User user = new User();

        when(shipService.isDistrictValid(userDetailDTO.getDistrictID(), userDetailDTO.getProvinceID(), authorizationHeader))
                .thenReturn(true);
        when(userService.updateUser(userDetailDTO, userId)).thenReturn(user);

        userFacade.updateUser(userDetailDTO, authorizationHeader);

        verify(jwtUtils).getIdFromJwtToken(authorizationHeader);
        verify(shipService).isDistrictValid(userDetailDTO.getDistrictID(), userDetailDTO.getProvinceID(), authorizationHeader);
        verify(userService).updateUser(userDetailDTO, userId);
    }

    @Test
    public void updateUser_ShouldThrowAddressException() {
        String authorizationHeader = "Bearer <token>";
        String userId = "user id";
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setFullName("fullName");
        userDetailDTO.setProvinceID(0);
        userDetailDTO.setDistrictID(0);
        userDetailDTO.setWardId(0);
        userDetailDTO.setPhone("phone");
        userDetailDTO.setGender("gender");
        userDetailDTO.setDob(new Date());
        when(jwtUtils.getIdFromJwtToken(authorizationHeader)).thenReturn(userId);

        when(shipService.isDistrictValid(userDetailDTO.getDistrictID(), userDetailDTO.getProvinceID(), authorizationHeader))
                .thenReturn(false);

        assertThrows(AddressException.class, () -> userFacade.updateUser(userDetailDTO, authorizationHeader));
    }

}
