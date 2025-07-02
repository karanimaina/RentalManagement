package com.students.tenant_management_systemt.repository;

import com.students.tenant_management_systemt.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
