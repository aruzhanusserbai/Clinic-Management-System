package com.example.demo.services.impl;

import com.example.demo.dtos.MedicalRecordRequestDto;
import com.example.demo.dtos.MedicalRecordResponseDto;
import com.example.demo.entities.MedicalRecord;
import com.example.demo.entities.User;
import com.example.demo.mappers.MedicalRecordMapper;
import com.example.demo.repositories.MedicalRecordRepository;
import com.example.demo.services.MedicalRecordService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private MedicalRecordMapper medicalRecordMapper;
    @Autowired
    private UserService userService;


    @Override
    public List<MedicalRecordResponseDto> getMyMedicalRecords(){
        User currentUser = userService.getCurrentUser();
        List<MedicalRecord> records = new ArrayList<>();

        records.addAll(medicalRecordRepository.findAllByPatient_Id(currentUser.getId()));

        return medicalRecordMapper.toDtoList(records);
    }

    @Override
    public List<MedicalRecordResponseDto> getMedicalRecordsByDoctor(){
        User currentUser = userService.getCurrentUser();
        List<MedicalRecord> records = new ArrayList<>();

        records.addAll(medicalRecordRepository.findAllByDoctor_Id(currentUser.getId()));

        return medicalRecordMapper.toDtoList(records);
    }

    @Override
    public List<MedicalRecordResponseDto> getAllMedicalRecords(){
        List<MedicalRecord> records = new ArrayList<>();
        records.addAll(medicalRecordRepository.findAll());

        return medicalRecordMapper.toDtoList(records);
    }


    @Override
    public MedicalRecordResponseDto createMedRecord(MedicalRecordRequestDto requestDto){
        User currentUser = userService.getCurrentUser();
        MedicalRecord medRecord = medicalRecordMapper.toEntity(requestDto);
        medRecord.setDoctor(currentUser);
        medicalRecordRepository.save(medRecord);
        return medicalRecordMapper.toDto(medRecord);
    }


    @Override
    public MedicalRecordResponseDto updateRecord(MedicalRecordRequestDto requestDto){
        MedicalRecord medRecord = medicalRecordRepository.findById(requestDto.getId()).orElse(null);

        if(Objects.isNull(medRecord)){
            return null;
        }

        MedicalRecord updated = medicalRecordMapper.toEntity(requestDto);
        if (updated.getDiagnosis() != null) {
            medRecord.setDiagnosis(updated.getDiagnosis());
        }
        if(updated.getTreatment()!=null){
            medRecord.setTreatment(updated.getTreatment());
        }
        if(updated.getRecordDate()!=null){
            medRecord.setRecordDate(updated.getRecordDate());
        }

        medicalRecordRepository.save(medRecord);
        return medicalRecordMapper.toDto(medRecord);
    }

}
