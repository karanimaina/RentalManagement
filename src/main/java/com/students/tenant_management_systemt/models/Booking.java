package com.students.tenant_management_systemt.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {
    private Long id;
    private Long houseId;
    private String houseName;
    private String tenantName;
    private String tenantEmail;
    private String tenantPhone;
    private int numberOfDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalAmount;
    private String bookingReference;
    private LocalDateTime bookingDateTime;
    private String status;
}