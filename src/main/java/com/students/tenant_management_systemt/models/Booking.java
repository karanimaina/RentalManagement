package com.students.tenant_management_systemt.models;

import com.students.tenant_management_systemt.dto.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity(name = "TB_BOOKING")
public class Booking extends BaseEntity{
    private Long houseId;
    private String apartmentName;
    private String tenantName;
    private String tenantEmail;
    private String tenantPhone;
    private int numberOfDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalAmount;
    @Column(unique = true)
    private String bookingReference;
    private LocalDateTime bookingDateTime;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(unique = true)
    private String houseCode;
}