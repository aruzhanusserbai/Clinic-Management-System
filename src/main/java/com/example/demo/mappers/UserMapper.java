package com.example.demo.mappers;

import com.example.demo.dtos.PermissionShortDto;
import com.example.demo.dtos.UserRequestDto;
import com.example.demo.dtos.UserResponseDto;
import com.example.demo.entities.Permission;
import com.example.demo.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto userRequestDto);

    UserResponseDto toDto(User user);

    PermissionShortDto toShortDto(Permission permission);
}
