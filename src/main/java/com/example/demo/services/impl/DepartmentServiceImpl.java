package com.example.demo.services.impl;

import com.example.demo.dtos.DepartmentDoctorDto;
import com.example.demo.dtos.DepartmentRequestDto;
import com.example.demo.dtos.DepartmentResponseDto;
import com.example.demo.entities.Department;
import com.example.demo.entities.DoctorDepartment;
import com.example.demo.entities.User;
import com.example.demo.mappers.DepartmentMapper;
import com.example.demo.mappers.DoctorDepartmentMapper;
import com.example.demo.repositories.DepartmentRepository;
import com.example.demo.repositories.DoctorDepartmentRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.DepartmentService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DoctorDepartmentRepository doctorDepartmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DoctorDepartmentMapper doctorDepartmentMapper;


    public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto){
        Department department = departmentMapper.toEntity(departmentRequestDto);

        departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }


    public void assignDoctorToDepartment(Long doctorId, Long departmentId) {
        User doctor = userRepository.findById(doctorId).orElse(null);

        Department department = departmentRepository.findById(departmentId).orElse(null);

        DoctorDepartment dd = new DoctorDepartment();
        dd.setDoctor(doctor);
        dd.setDepartment(department);

        doctorDepartmentRepository.save(dd);
    }



    @Override
    public List<DepartmentDoctorDto> getMyDepartments(){
        User doctor = userService.getCurrentUser();
        List<DoctorDepartment> departments = doctorDepartmentRepository.findAllByDoctor_Id(doctor.getId());

        return doctorDepartmentMapper.toDtoList(departments);
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartments(){
        List<Department> departments = departmentRepository.findAll();
        return departmentMapper.toDtoList(departments);
    }

}
