package com.example.demo.controllers;

import com.example.demo.dtos.AssignDoctorDepartmentRequestDto;
import com.example.demo.dtos.DepartmentDoctorDto;
import com.example.demo.dtos.DepartmentRequestDto;
import com.example.demo.dtos.DepartmentResponseDto;
import com.example.demo.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/admin/create-department")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createDepartment(@RequestBody DepartmentRequestDto departmentDto) {
        DepartmentResponseDto newDepartment = departmentService.createDepartment(departmentDto);

        if (Objects.nonNull(newDepartment)) {
            return ResponseEntity.ok(newDepartment);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/admin/assign-doctor-department")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> assignDoctorToDepartment(@RequestBody AssignDoctorDepartmentRequestDto requestDto){
        departmentService.assignDoctorToDepartment(requestDto.getDoctorId(), requestDto.getDepartmentId());
        return ResponseEntity.ok("Doctor assigned to department successfully");
    }


    @GetMapping("/doctor/get-my-departments")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<?> getDepartments(){
        List<DepartmentDoctorDto> departmentDoctorDtoList = departmentService.getMyDepartments();
        return ResponseEntity.ok(departmentDoctorDtoList);
    }


    @GetMapping("/admin/get-all-departments")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllDepartments(){
        List<DepartmentResponseDto> departmentList = departmentService.getAllDepartments();
        return ResponseEntity.ok(departmentList);
    }
}
