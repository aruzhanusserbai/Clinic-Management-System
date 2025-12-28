package com.example.demo.services;

import com.example.demo.dtos.*;
import com.example.demo.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getCurrentUser();
    UserResponseDto signUp(UserRequestDto userRequestDto);
    Boolean updatePassword(ChangePasswordRequestDto passwords);
    UserResponseDto updateProfile(UserRequestDto userRequestDto);
    UserResponseDto createUserByAdmin(AdminCreateUserRequestDto userRequestDto);
    Boolean deleteUserByAdmin(Long id);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUserByAdmin(Long id, UserRequestDto userRequestDto);
    UserResponseDto assignRoleByAdmin(Long userId, Long roleId);
    UserResponseDto removeRoleByAdmin(Long userId, Long roleId);

}
