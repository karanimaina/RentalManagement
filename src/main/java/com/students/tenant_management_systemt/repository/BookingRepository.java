package com.students.tenant_management_systemt.repository;

import com.students.tenant_management_systemt.dto.Status;
import com.students.tenant_management_systemt.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    Boolean existsByBookingReference(String bookingReference);
    Boolean existsByHouseCode(String houseCode);
    Optional<Booking> findByBookingReference(String reference);
    List<Booking> findByHouseIdAndStatus(Long id, Status status);
    List<Booking> findByTenantEmail(String email);
    List<Booking> findByHouseId(Long houseId);
    List<Booking> findByStatus(Status status);
    List<Booking> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
    long countByStatus(Status status);
}
