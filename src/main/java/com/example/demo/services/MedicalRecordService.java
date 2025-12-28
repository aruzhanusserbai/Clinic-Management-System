package com.example.demo.services;


import com.example.demo.dtos.MedicalRecordRequestDto;
import com.example.demo.dtos.MedicalRecordResponseDto;

import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecordResponseDto> getMyMedicalRecords();
    List<MedicalRecordResponseDto> getMedicalRecordsByDoctor();
    List<MedicalRecordResponseDto> getAllMedicalRecords();

    MedicalRecordResponseDto createMedRecord(MedicalRecordRequestDto requestDto);
    MedicalRecordResponseDto updateRecord(MedicalRecordRequestDto requestDto);
}
