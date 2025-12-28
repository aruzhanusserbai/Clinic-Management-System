package com.example.demo.services;

import com.example.demo.dtos.PaymentRequestDto;
import com.example.demo.dtos.PaymentResponseDto;

import java.util.List;

public interface PaymentService {
    List<PaymentResponseDto> getMyPayments();
    List<PaymentResponseDto> getAllPayments();
    PaymentResponseDto createPayment(PaymentRequestDto requestDto);
    PaymentResponseDto updateStatus(Long id, String status);

}
