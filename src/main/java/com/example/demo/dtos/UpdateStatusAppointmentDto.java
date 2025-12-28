package com.example.demo.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusAppointmentDto {
    private Long id;
    private String status;
}
