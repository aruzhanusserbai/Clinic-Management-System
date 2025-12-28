package com.example.demo.services.impl;

import com.example.demo.dtos.MedicalRecordRequestDto;
import com.example.demo.dtos.MedicalRecordResponseDto;
import com.example.demo.entities.MedicalRecord;
import com.example.demo.entities.User;
import com.example.demo.mappers.MedicalRecordMapper;
import com.example.demo.repositories.MedicalRecordRepository;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceImplTest {
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private MedicalRecordMapper medicalRecordMapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    private User currentUser;

    @BeforeEach
    void setUp() {
        currentUser = new User();
        currentUser.setId(1L);
    }


    @Test
    void testGetMyMedicalRecords() {
        List<MedicalRecord> records = new ArrayList<>();
        MedicalRecord record = new MedicalRecord();
        records.add(record);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(medicalRecordRepository.findAllByPatient_Id(currentUser.getId())).thenReturn(records);
        when(medicalRecordMapper.toDtoList(records)).thenReturn(new ArrayList<>());


        List<MedicalRecordResponseDto> result = medicalRecordService.getMyMedicalRecords();
        assertNotNull(result);
        verify(medicalRecordRepository).findAllByPatient_Id(currentUser.getId());
        verify(medicalRecordMapper).toDtoList(records);
    }


    @Test
    void testGetMedicalRecordsByDoctor() {
        List<MedicalRecord> records = new ArrayList<>();
        MedicalRecord record = new MedicalRecord();
        records.add(record);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(medicalRecordRepository.findAllByDoctor_Id(currentUser.getId())).thenReturn(records);
        when(medicalRecordMapper.toDtoList(records)).thenReturn(new ArrayList<>());

        List<MedicalRecordResponseDto> result = medicalRecordService.getMedicalRecordsByDoctor();
        assertNotNull(result);
        verify(medicalRecordRepository).findAllByDoctor_Id(currentUser.getId());
        verify(medicalRecordMapper).toDtoList(records);
    }

    @Test
    void testGetAllMedicalRecords() {
        List<MedicalRecord> records = new ArrayList<>();
        MedicalRecord record = new MedicalRecord();
        records.add(record);

        when(medicalRecordRepository.findAll()).thenReturn(records);
        when(medicalRecordMapper.toDtoList(records)).thenReturn(new ArrayList<>());



        List<MedicalRecordResponseDto> result = medicalRecordService.getAllMedicalRecords();
        assertNotNull(result);
        verify(medicalRecordRepository).findAll();
        verify(medicalRecordMapper).toDtoList(records);
    }



    @Test
    void testCreateMedRecord() {
        MedicalRecordRequestDto dto = new MedicalRecordRequestDto();
        MedicalRecord recordEntity = new MedicalRecord();
        MedicalRecordResponseDto responseDto = new MedicalRecordResponseDto();

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(medicalRecordMapper.toEntity(dto)).thenReturn(recordEntity);
        when(medicalRecordMapper.toDto(recordEntity)).thenReturn(responseDto);

        MedicalRecordResponseDto result = medicalRecordService.createMedRecord(dto);
        assertNotNull(result);
        verify(medicalRecordRepository).save(recordEntity);
        assertEquals(currentUser, recordEntity.getDoctor());
    }


    @Test
    void testUpdateRecord_Success() {
        MedicalRecordRequestDto dto = new MedicalRecordRequestDto();
        dto.setId(1L);
        dto.setDiagnosis("New Diagnosis");
        dto.setTreatment("New Treatment");
        dto.setRecordDate(LocalDateTime.now());

        MedicalRecord existing = new MedicalRecord();
        MedicalRecord updatedEntity = new MedicalRecord();
        updatedEntity.setDiagnosis(dto.getDiagnosis());
        updatedEntity.setTreatment(dto.getTreatment());
        updatedEntity.setRecordDate(dto.getRecordDate());

        MedicalRecordResponseDto responseDto = new MedicalRecordResponseDto();

        when(medicalRecordRepository.findById(dto.getId())).thenReturn(Optional.of(existing));
        when(medicalRecordMapper.toEntity(dto)).thenReturn(updatedEntity);
        when(medicalRecordMapper.toDto(existing)).thenReturn(responseDto);

        MedicalRecordResponseDto result = medicalRecordService.updateRecord(dto);

        assertNotNull(result);
        assertEquals("New Diagnosis", existing.getDiagnosis());
        assertEquals("New Treatment", existing.getTreatment());
        verify(medicalRecordRepository).save(existing);
    }



    @Test
    void testUpdateRecord_NotFound() {
        MedicalRecordRequestDto dto = new MedicalRecordRequestDto();
        dto.setId(1L);

        when(medicalRecordRepository.findById(dto.getId())).thenReturn(Optional.empty());

        MedicalRecordResponseDto result = medicalRecordService.updateRecord(dto);
        assertNull(result);
        verify(medicalRecordRepository, never()).save(any());
    }
}
