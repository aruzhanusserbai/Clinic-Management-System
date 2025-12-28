package com.example.demo.mappers;

import com.example.demo.dtos.PaymentRequestDto;
import com.example.demo.dtos.PaymentResponseDto;
import com.example.demo.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(source = "patient.fullName", target = "patientName")
    @Mapping(source = "appointment.id", target = "appointmentId")
    PaymentResponseDto toDto(Payment payment);

    List<PaymentResponseDto> toDtoList(List<Payment> payment);

    @Mapping(source = "appointmentId", target = "appointment.id")
    Payment toEntity(PaymentRequestDto requestDto);
}
