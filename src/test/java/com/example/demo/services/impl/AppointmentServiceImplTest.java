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
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private DoctorDepartmentMapper doctorDepartmentMapper;
    @Mock
    private DoctorDepartmentRepository doctorDepartmentRepository;
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserService userService;
    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;


    private User currentUser;

    @BeforeEach
    void setUp() {
        currentUser = new User();
        currentUser.setId(1L);
        lenient().when(userService.getCurrentUser()).thenReturn(currentUser);
    }



    @Test
    void testGetDoctors() {
        DoctorDepartment dd = new DoctorDepartment();
        when(doctorDepartmentRepository.findAll()).thenReturn(List.of(dd));
        DepartmentDoctorDto dto = new DepartmentDoctorDto();
        when(doctorDepartmentMapper.toDto(dd)).thenReturn(dto);

        List<DepartmentDoctorDto> result = appointmentService.getDoctors();

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
        verify(doctorDepartmentRepository).findAll();
        verify(doctorDepartmentMapper).toDto(dd);
    }


    @Test
    void testGetMyAppointments() {
        Appointment appointment = new Appointment();
        when(appointmentRepository.findAllByPatient_Id(currentUser.getId()))
                .thenReturn(List.of(appointment));
        AppointmentResponseDto dto = new AppointmentResponseDto();
        when(appointmentMapper.toDtoList(List.of(appointment)))
                .thenReturn(List.of(dto));

        List<AppointmentResponseDto> result = appointmentService.getMyAppointments();

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
        verify(appointmentRepository).findAllByPatient_Id(currentUser.getId());
        verify(appointmentMapper).toDtoList(List.of(appointment));
    }




    @Test
    void testGetAppointmentsByDoctor() {
        Appointment appointment = new Appointment();
        when(appointmentRepository.findAllByDoctor_Id(currentUser.getId()))
                .thenReturn(List.of(appointment));
        AppointmentResponseDto dto = new AppointmentResponseDto();
        when(appointmentMapper.toDtoList(List.of(appointment)))
                .thenReturn(List.of(dto));

        List<AppointmentResponseDto> result = appointmentService.getAppointmentsByDoctor();

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
        verify(appointmentRepository).findAllByDoctor_Id(currentUser.getId());
        verify(appointmentMapper).toDtoList(List.of(appointment));
    }



    @Test
    void testGetAllAppointments() {
        Appointment appointment = new Appointment();
        when(appointmentRepository.findAll()).thenReturn(List.of(appointment));
        AppointmentResponseDto dto = new AppointmentResponseDto();
        when(appointmentMapper.toDtoList(List.of(appointment))).thenReturn(List.of(dto));

        List<AppointmentResponseDto> result = appointmentService.getAllAppointments();

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
        verify(appointmentRepository).findAll();
        verify(appointmentMapper).toDtoList(List.of(appointment));
    }

    @Test
    void testCreateAppointment() {
        AppointmentRequestDto requestDto = new AppointmentRequestDto();
        Appointment appointment = new Appointment();
        when(appointmentMapper.toEntity(requestDto)).thenReturn(appointment);

        appointmentService.createAppointment(requestDto);

        assertEquals(currentUser, appointment.getPatient());
        verify(appointmentRepository).save(appointment);
        verify(appointmentMapper).toEntity(requestDto);
    }



    @Test
    void testUpdateStatus() {
        Appointment appointment = new Appointment();
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        AppointmentResponseDto dto = new AppointmentResponseDto();
        when(appointmentMapper.toDto(appointment)).thenReturn(dto);

        AppointmentResponseDto result = appointmentService.updateStatus(1L, "CONFIRMED");

        assertEquals("CONFIRMED", appointment.getStatus());
        assertSame(dto, result);
        verify(appointmentRepository).findById(1L);
        verify(appointmentRepository).save(appointment);
        verify(appointmentMapper).toDto(appointment);
    }
}
