package com.example.demo.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordResponseDto {
    private Long id;
    private Long patientId;
    private String patientFullName;

    private Long doctorId;
    private String doctorFullName;

    private String diagnosis;
    private String treatment;
    private LocalDateTime recordDate;
}
