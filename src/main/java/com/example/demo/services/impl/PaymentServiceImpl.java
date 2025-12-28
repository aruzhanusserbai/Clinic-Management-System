package com.example.demo.services.impl;

import com.example.demo.dtos.PaymentRequestDto;
import com.example.demo.dtos.PaymentResponseDto;
import com.example.demo.entities.Appointment;
import com.example.demo.entities.Payment;
import com.example.demo.entities.User;
import com.example.demo.mappers.PaymentMapper;
import com.example.demo.repositories.PaymentRepository;
import com.example.demo.services.PaymentService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private UserService userService;

    @Override
    public List<PaymentResponseDto> getMyPayments(){
        User currentUser = userService.getCurrentUser();

        List<Payment> paymentList = paymentRepository.findByPatient(currentUser);
        return paymentMapper.toDtoList(paymentList);

    }

    @Override
    public List<PaymentResponseDto> getAllPayments(){
        List<Payment> paymentList = paymentRepository.findAll();
        return paymentMapper.toDtoList(paymentList);
    }


    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto){
        User currentPatient = userService.getCurrentUser();
        Payment payment = paymentMapper.toEntity(requestDto);
        payment.setPatient(currentPatient);
        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentResponseDto updateStatus(Long id, String status){
        Payment payment = paymentRepository.findById(id).orElse(null);
        if(Objects.isNull(payment)){
            return null;
        }
        payment.setStatus(status);
        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

}
