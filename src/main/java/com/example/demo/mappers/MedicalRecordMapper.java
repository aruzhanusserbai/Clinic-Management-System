package com.example.demo.mappers;

import com.example.demo.dtos.MedicalRecordRequestDto;
import com.example.demo.dtos.MedicalRecordResponseDto;
import com.example.demo.entities.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "doctor.fullName", target = "doctorFullName")

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient.fullName", target = "patientFullName")

    MedicalRecordResponseDto toDto(MedicalRecord record);

    @Mapping(source = "patientId", target = "patient.id")
    MedicalRecord toEntity(MedicalRecordRequestDto dto);

    List<MedicalRecordResponseDto> toDtoList(List<MedicalRecord> records);

}
