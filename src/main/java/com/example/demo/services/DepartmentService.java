package com.example.demo.services;

import com.example.demo.dtos.DepartmentDoctorDto;
import com.example.demo.dtos.DepartmentRequestDto;
import com.example.demo.dtos.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {
    DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto);
    void assignDoctorToDepartment(Long doctorId, Long departmentId);
    List<DepartmentDoctorDto> getMyDepartments();
    List<DepartmentResponseDto> getAllDepartments();
}
