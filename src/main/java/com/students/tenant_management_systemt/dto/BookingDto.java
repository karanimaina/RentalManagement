package com.students.tenant_management_systemt.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingDto(
          Long houseId,
          String apartmentName,
          String tenantName ,
          String tenantEmail ,
          String tenantPhone ,
          int numberOfDays ,
          LocalDate startDate ,
          LocalDate endDate ,
          double totalAmount ,
          String bookingReference ,
          LocalDateTime bookingDateTime ,
          String status ,
          String houseCode
) {
}
