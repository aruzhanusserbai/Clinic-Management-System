package com.example.demo.mappers;

import com.example.demo.dtos.AppointmentRequestDto;
import com.example.demo.dtos.AppointmentResponseDto;
import com.example.demo.entities.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(source = "patient.fullName", target = "patientFullName")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "doctor.fullName", target = "doctorFullName")
    @Mapping(source = "doctor.id", target = "doctorId")
    AppointmentResponseDto toDto(Appointment appointment);

    @Mapping(source = "doctorId", target = "doctor.id")
    Appointment toEntity(AppointmentRequestDto requestDto);

    List<AppointmentResponseDto> toDtoList(List<Appointment> appointments);
}
