package com.example.demo.services;


import com.example.demo.dtos.AppointmentRequestDto;
import com.example.demo.dtos.AppointmentResponseDto;
import com.example.demo.dtos.DepartmentDoctorDto;

import java.util.List;

public interface AppointmentService {
    List<DepartmentDoctorDto> getDoctors();
    List<AppointmentResponseDto> getMyAppointments();
    List<AppointmentResponseDto> getAppointmentsByDoctor();
    List<AppointmentResponseDto> getAllAppointments();

    void createAppointment(AppointmentRequestDto requestDto);
    AppointmentResponseDto updateStatus(Long id, String status);
}
