package com.example.demo.dtos;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDto {
    private Long doctorId;
    private LocalDate appointmentDate;
    private String notes;
}
