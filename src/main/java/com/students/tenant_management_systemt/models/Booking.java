package com.students.tenant_management_systemt.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_BOOKING")
public class Booking extends BaseEntity{
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