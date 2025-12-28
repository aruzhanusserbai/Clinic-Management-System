package com.example.demo.services.impl;

import com.example.demo.dtos.*;
import com.example.demo.entities.Permission;
import com.example.demo.entities.User;
import com.example.demo.mappers.UserMapper;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;
    @Mock
    private PermissionRepository permissionRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User currentUser;



    @BeforeEach
    void setUp() {
        currentUser = new User();
        currentUser.setId(1L);
        currentUser.setPassword("encodedPassword");
        currentUser.setRoles(new ArrayList<>());
    }


    @Test
    void testSignUp_NewUser_Success() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("test@test.com");
        dto.setFullName("Test User");
        dto.setPassword("pass");

        User userEntity = new User();
        userEntity.setEmail(dto.getEmail());
        userEntity.setFullName(dto.getFullName());
        userEntity.setPassword(dto.getPassword());

        when(userMapper.toEntity(dto)).thenReturn(userEntity);
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(null);
        Permission patientRole = new Permission();
        patientRole.setPermission("PATIENT");
        when(permissionRepository.findByPermission("PATIENT")).thenReturn(patientRole);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setEmail(dto.getEmail());
        savedUser.setFullName(dto.getFullName());
        savedUser.setPassword("encodedPassword");
        savedUser.setRoles(List.of(patientRole));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toDto(any(User.class))).thenReturn(new UserResponseDto());

        UserResponseDto result = userService.signUp(dto);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdatePassword_Success() {
        ChangePasswordRequestDto dto = new ChangePasswordRequestDto();
        dto.setOldPassword("old");
        dto.setNewPassword("new");

        when(userRepository.save(currentUser)).thenReturn(currentUser);
        when(passwordEncoder.matches("old", currentUser.getPassword())).thenReturn(true);
        when(passwordEncoder.encode("new")).thenReturn("encodedNew");

        // мок getCurrentUser
        UserServiceImpl spyService = spy(userService);
        doReturn(currentUser).when(spyService).getCurrentUser();

        Boolean result = spyService.updatePassword(dto);

        assertTrue(result);
        assertEquals("encodedNew", currentUser.getPassword());
        verify(userRepository).save(currentUser);
    }

    @Test
    void testUpdateProfile_Success() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("new@test.com");
        dto.setFullName("New Name");

        when(userMapper.toEntity(dto)).thenReturn(new User());
        when(userRepository.save(currentUser)).thenReturn(currentUser);
        when(userMapper.toDto(currentUser)).thenReturn(new UserResponseDto());

        UserServiceImpl spyService = spy(userService);
        doReturn(currentUser).when(spyService).getCurrentUser();

        UserResponseDto result = spyService.updateProfile(dto);

        assertNotNull(result);
        verify(userRepository).save(currentUser);
    }

    @Test
    void testCreateUserByAdmin_Success() {
        AdminCreateUserRequestDto dto = new AdminCreateUserRequestDto();
        dto.setEmail("admin@test.com");
        dto.setFullName("Admin User");
        dto.setPassword("pass");
        dto.setRole("PATIENT");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(null);
        Permission role = new Permission();
        when(permissionRepository.findByPermission(dto.getRole())).thenReturn(role);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encoded");
        User savedUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toDto(any(User.class))).thenReturn(new UserResponseDto());

        UserResponseDto result = userService.createUserByAdmin(dto);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testDeleteUserByAdmin_Success() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Boolean result = userService.deleteUserByAdmin(id);

        assertTrue(result);
        verify(userRepository).deleteById(id);
    }

    @Test
    void testUpdateUserByAdmin_Success() {
        Long id = 1L;
        UserRequestDto dto = new UserRequestDto();
        User existingUser = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userMapper.toEntity(dto)).thenReturn(new User());
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(userMapper.toDto(existingUser)).thenReturn(new UserResponseDto());

        UserResponseDto result = userService.updateUserByAdmin(id, dto);

        assertNotNull(result);
        verify(userRepository).save(existingUser);
    }

    @Test
    void testAssignRoleByAdmin_Success() {
        Long userId = 1L;
        Long roleId = 1L;
        User user = new User();
        user.setRoles(new ArrayList<>());
        Permission role = new Permission();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(permissionRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(new UserResponseDto());

        UserResponseDto result = userService.assignRoleByAdmin(userId, roleId);

        assertNotNull(result);
        assertTrue(user.getRoles().contains(role));
        verify(userRepository).save(user);
    }

    @Test
    void testRemoveRoleByAdmin_Success() {
        Long userId = 1L;
        Long roleId = 1L;
        Permission role = new Permission();
        User user = new User();
        user.setRoles(new ArrayList<>(List.of(role)));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(permissionRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(new UserResponseDto());

        UserResponseDto result = userService.removeRoleByAdmin(userId, roleId);

        assertNotNull(result);
        assertFalse(user.getRoles().contains(role));
        verify(userRepository).save(user);
    }
}
