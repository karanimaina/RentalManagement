package com.students.tenant_management_systemt.dto;

public record PaymentDto(
        String bookingReference,
        Double amount,
        String paymentMethod,
        String cardNumber,
        String expiryDate,
        String cvv
) {}