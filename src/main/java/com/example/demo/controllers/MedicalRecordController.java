package com.example.demo.controllers;

import com.example.demo.dtos.*;
import com.example.demo.services.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;


    @GetMapping("/get-my-medRecords")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    public ResponseEntity<?> getMedRecords(){
        List<MedicalRecordResponseDto> records = medicalRecordService.getMyMedicalRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/get-doctor-medRecords")
    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    public ResponseEntity<?> getMedicalRecordsByDoctor(){
        List<MedicalRecordResponseDto> records = medicalRecordService.getMedicalRecordsByDoctor();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/get-all-medRecords")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAllMedicalRecords(){
        List<MedicalRecordResponseDto> records = medicalRecordService.getAllMedicalRecords();
        return ResponseEntity.ok(records);
    }


    @PostMapping("/doctor/create-medRecord")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<?> createMedRecord(@RequestBody MedicalRecordRequestDto requestDto){
        MedicalRecordResponseDto responseDto = medicalRecordService.createMedRecord(requestDto);
        return ResponseEntity.ok(responseDto);
    }


    @PatchMapping("/doctor/update-medRecord")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<?> updateMedRecord(@RequestBody MedicalRecordRequestDto requestDto){
        MedicalRecordResponseDto responseDto = medicalRecordService.updateRecord(requestDto);

        if(Objects.isNull(responseDto)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(responseDto);
        }
    }
}
