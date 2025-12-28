package com.example.demo.services.impl;

import com.example.demo.dtos.AppointmentRequestDto;
import com.example.demo.dtos.AppointmentResponseDto;
import com.example.demo.dtos.DepartmentDoctorDto;
import com.example.demo.entities.Appointment;
import com.example.demo.entities.DoctorDepartment;
import com.example.demo.entities.User;
import com.example.demo.mappers.AppointmentMapper;
import com.example.demo.mappers.DoctorDepartmentMapper;
import com.example.demo.repositories.AppointmentRepository;
import com.example.demo.repositories.DoctorDepartmentRepository;
import com.example.demo.services.AppointmentService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private DoctorDepartmentMapper doctorDepartmentMapper;
    @Autowired
    private DoctorDepartmentRepository doctorDepartmentRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public List<DepartmentDoctorDto> getDoctors() {
        List<DoctorDepartment> doctorDepartments = doctorDepartmentRepository.findAll();
        List<DepartmentDoctorDto> list = new ArrayList<>();
        doctorDepartments.forEach(docDeps->
                list.add(doctorDepartmentMapper.toDto(docDeps)));
        return list;
    }

    @Override
    public List<AppointmentResponseDto> getMyAppointments(){
        User currentUser = userService.getCurrentUser();
        List<Appointment> appointments = new ArrayList<>();
        appointments.addAll(appointmentRepository.findAllByPatient_Id(currentUser.getId()));

        return appointmentMapper.toDtoList(appointments);

    }


    @Override
    public List<AppointmentResponseDto> getAppointmentsByDoctor(){
        User currentUser = userService.getCurrentUser();
        List<Appointment> appointments = new ArrayList<>();
        appointments.addAll(appointmentRepository.findAllByDoctor_Id(currentUser.getId()));

        return appointmentMapper.toDtoList(appointments);
    }


    @Override
    public List<AppointmentResponseDto> getAllAppointments(){
        List<Appointment> appointments = new ArrayList<>();
        appointments.addAll(appointmentRepository.findAll());

        return appointmentMapper.toDtoList(appointments);
    }


    @Override
    public void createAppointment(AppointmentRequestDto requestDto){
        User currentUser = userService.getCurrentUser();
        Appointment appointment = appointmentMapper.toEntity(requestDto);
        appointment.setPatient(currentUser);
        appointmentRepository.save(appointment);
    }


    @Override
    public AppointmentResponseDto updateStatus(Long id, String status){
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
        return appointmentMapper.toDto(appointment);
    }


}
