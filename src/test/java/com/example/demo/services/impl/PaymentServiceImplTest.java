package com.example.demo.services.impl;

import com.example.demo.dtos.PaymentRequestDto;
import com.example.demo.dtos.PaymentResponseDto;
import com.example.demo.entities.Payment;
import com.example.demo.entities.User;
import com.example.demo.mappers.PaymentMapper;
import com.example.demo.repositories.PaymentRepository;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentMapper paymentMapper;
    @Mock
    private UserService userService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private User currentUser;

    @BeforeEach
    void setUp() {
        currentUser = new User();
        currentUser.setId(1L);
    }


    @Test
    void testGetMyPayments() {
        List<Payment> payments = new ArrayList<>();
        Payment payment = new Payment();
        payments.add(payment);
        List<PaymentResponseDto> dtoList = new ArrayList<>();

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(paymentRepository.findByPatient(currentUser)).thenReturn(payments);
        when(paymentMapper.toDtoList(payments)).thenReturn(dtoList);

        List<PaymentResponseDto> result = paymentService.getMyPayments();
        assertNotNull(result);
        verify(paymentRepository).findByPatient(currentUser);
        verify(paymentMapper).toDtoList(payments);

    }



    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        Payment payment = new Payment();
        payments.add(payment);
        List<PaymentResponseDto> dtoList = new ArrayList<>();

        when(paymentRepository.findAll()).thenReturn(payments);
        when(paymentMapper.toDtoList(payments)).thenReturn(dtoList);

        List<PaymentResponseDto> result = paymentService.getAllPayments();
        assertNotNull(result);
        verify(paymentRepository).findAll();
        verify(paymentMapper).toDtoList(payments);
    }


    @Test
    void testCreatePayment() {
        PaymentRequestDto dto = new PaymentRequestDto();
        Payment paymentEntity = new Payment();
        PaymentResponseDto responseDto = new PaymentResponseDto();

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(paymentMapper.toEntity(dto)).thenReturn(paymentEntity);
        when(paymentMapper.toDto(paymentEntity)).thenReturn(responseDto);


        PaymentResponseDto result = paymentService.createPayment(dto);


        assertNotNull(result);
        verify(paymentRepository).save(paymentEntity);
        assertEquals(currentUser, paymentEntity.getPatient());
    }

    @Test
    void testUpdateStatus_Success() {
        Long id = 1L;
        String status = "PAID";
        Payment payment = new Payment();
        PaymentResponseDto responseDto = new PaymentResponseDto();

        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));
        when(paymentMapper.toDto(payment)).thenReturn(responseDto);

        PaymentResponseDto result = paymentService.updateStatus(id, status);
        assertNotNull(result);
        assertEquals(status, payment.getStatus());
        verify(paymentRepository).save(payment);
    }

    @Test
    void testUpdateStatus_NotFound() {
        Long id = 1L;
        String status = "PAID";

        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        PaymentResponseDto result = paymentService.updateStatus(id, status);
        assertNull(result);
        verify(paymentRepository, never()).save(any());
    }
}
