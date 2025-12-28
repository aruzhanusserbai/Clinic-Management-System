package com.example.demo.mappers;

import com.example.demo.dtos.DepartmentRequestDto;
import com.example.demo.dtos.DepartmentResponseDto;
import com.example.demo.entities.Department;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentResponseDto toDto(Department department);
    List<DepartmentResponseDto> toDtoList(List<Department> departments);
    Department toEntity(DepartmentRequestDto departmentRequestDto);
}
