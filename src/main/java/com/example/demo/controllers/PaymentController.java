package com.example.demo.controllers;

import com.example.demo.dtos.PaymentRequestDto;
import com.example.demo.dtos.PaymentResponseDto;
import com.example.demo.dtos.UpdateStatusAppointmentDto;
import com.example.demo.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/get-my-payments")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<?> getMyPayments() {
        List<PaymentResponseDto> payments = paymentService.getMyPayments();

        if (Objects.isNull(payments)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(payments);
        }
    }

    @GetMapping("/get-all-payments")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllPayments() {
        List<PaymentResponseDto> payments = paymentService.getAllPayments();

        if (Objects.isNull(payments)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(payments);
        }
    }

    @PostMapping("/patient/pay-for-appointment")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequestDto requestDto) {
        PaymentResponseDto payment = paymentService.createPayment(requestDto);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/admin/update-payment-status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusAppointmentDto payment){
        PaymentResponseDto paymentResponseDto = paymentService.updateStatus(payment.getId(), payment.getStatus());

        if(Objects.isNull(paymentResponseDto)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(paymentResponseDto);
        }
    }
}