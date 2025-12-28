package com.example.demo.dtos;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private String patientName;
    private Long appointmentId;
    private BigDecimal amount;
    private String status;
}
