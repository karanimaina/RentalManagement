package com.students.tenant_management_systemt.models;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TB_BOOKING_REQUEST")
@Builder
public class BookingRequest  extends BaseEntity{
    private Long houseId;
    private String tenantName;
    private String tenantEmail;
    private String tenantPhone;
    private int numberOfDays;
    private LocalDate startDate;

}
