package com.example.demo.controllers;

import com.example.demo.dtos.*;
import com.example.demo.services.impl.AppointmentServiceImpl;
import com.example.demo.services.impl.DepartmentServiceImpl;
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
public class AppointmentController {
    private final AppointmentServiceImpl appointmentService;
    private final DepartmentServiceImpl departmentService;


    @GetMapping("/patient/get-doctors")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<?> getDoctors(){
        List<DepartmentDoctorDto> doctors = appointmentService.getDoctors();

        if(doctors.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(doctors);
        }
    }


    @GetMapping("/get-my-appointments")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<?> getMyAppointments(){
        List<AppointmentResponseDto> responseDtos = appointmentService.getMyAppointments();
        return ResponseEntity.ok(responseDtos);
    }


    @GetMapping("/get-doctor-appointments")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<?> getAppointmentsDoctor(){
        List<AppointmentResponseDto> responseDtos = appointmentService.getAppointmentsByDoctor();
        return ResponseEntity.ok(responseDtos);
    }


    @GetMapping("/get-all-appointments")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllAppointments(){
        List<AppointmentResponseDto> responseDtos = appointmentService.getAllAppointments();
        return ResponseEntity.ok(responseDtos);
    }


    @PostMapping("/patient/create-appointment")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequestDto requestDto){
        appointmentService.createAppointment(requestDto);
        return ResponseEntity.ok("Appointment created succesfully!");
    }


    @PostMapping("/doctor/update-appointment-status")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusAppointmentDto appointmentDto){
        AppointmentResponseDto appointmentResponseDto = appointmentService.updateStatus(appointmentDto.getId(), appointmentDto.getStatus());

        if(Objects.isNull(appointmentResponseDto)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(appointmentResponseDto);
        }
    }

}
