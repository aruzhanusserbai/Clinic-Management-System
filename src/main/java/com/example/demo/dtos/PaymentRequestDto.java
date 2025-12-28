package com.example.demo.dtos;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {

    private Long appointmentId;
    private BigDecimal amount;
    private String method;
}
