package com.example.demo.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordRequestDto {
    private Long id;
    private Long patientId;
    private String diagnosis;
    private String treatment;
    private LocalDateTime recordDate;
}
