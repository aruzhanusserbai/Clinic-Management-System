package com.example.demo.mappers;

import com.example.demo.dtos.DepartmentDoctorDto;
import com.example.demo.entities.DoctorDepartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorDepartmentMapper {
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "doctor.fullName", target = "doctorFullName")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    DepartmentDoctorDto toDto(DoctorDepartment entity);

    List<DepartmentDoctorDto> toDtoList(List<DoctorDepartment> departments);
}
