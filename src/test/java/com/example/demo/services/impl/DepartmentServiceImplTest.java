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
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private DepartmentMapper departmentMapper;

    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private DoctorDepartmentRepository doctorDepartmentRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private DoctorDepartmentMapper doctorDepartmentMapper;

    @Mock
    private UserService userService;
    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private User currentDoctor;



    @BeforeEach
    void setUp() {
        currentDoctor = new User();
        currentDoctor.setId(1L);
    }



    @Test
    void testCreateDepartment() {
        DepartmentRequestDto dto = new DepartmentRequestDto();
        Department departmentEntity = new Department();
        DepartmentResponseDto responseDto = new DepartmentResponseDto();

        when(departmentMapper.toEntity(dto)).thenReturn(departmentEntity);
        when(departmentMapper.toDto(departmentEntity)).thenReturn(responseDto);

        DepartmentResponseDto result = departmentService.createDepartment(dto);
        assertNotNull(result);
        verify(departmentRepository).save(departmentEntity);
        verify(departmentMapper).toDto(departmentEntity);
    }


    @Test
    void testAssignDoctorToDepartment() {
        Long doctorId = 1L;
        Long departmentId = 2L;
        User doctor = new User();
        Department department = new Department();

        when(userRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        departmentService.assignDoctorToDepartment(doctorId, departmentId);
        verify(doctorDepartmentRepository).save(any(DoctorDepartment.class));
    }



    @Test
    void testGetMyDepartments() {
        List<DoctorDepartment> doctorDepartments = new ArrayList<>();
        DoctorDepartment dd = new DoctorDepartment();
        doctorDepartments.add(dd);
        List<DepartmentDoctorDto> dtoList = new ArrayList<>();

        when(userService.getCurrentUser()).thenReturn(currentDoctor);
        when(doctorDepartmentRepository.findAllByDoctor_Id(currentDoctor.getId())).thenReturn(doctorDepartments);
        when(doctorDepartmentMapper.toDtoList(doctorDepartments)).thenReturn(dtoList);

        List<DepartmentDoctorDto> result = departmentService.getMyDepartments();
        assertNotNull(result);
        verify(doctorDepartmentRepository).findAllByDoctor_Id(currentDoctor.getId());
        verify(doctorDepartmentMapper).toDtoList(doctorDepartments);
    }

    @Test
    void testGetAllDepartments() {
        List<Department> departments = new ArrayList<>();
        Department department = new Department();
        departments.add(department);

        List<DepartmentResponseDto> dtoList = new ArrayList<>();


        when(departmentRepository.findAll()).thenReturn(departments);
        when(departmentMapper.toDtoList(departments)).thenReturn(dtoList);

        List<DepartmentResponseDto> result = departmentService.getAllDepartments();
        assertNotNull(result);
        verify(departmentRepository).findAll();
        verify(departmentMapper).toDtoList(departments);
    }
}
