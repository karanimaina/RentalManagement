package com.students.tenant_management_systemt.controller;

import com.students.tenant_management_systemt.dto.HouseDto;
import com.students.tenant_management_systemt.dto.BookingDto;
import com.students.tenant_management_systemt.dto.PaymentDto;
import com.students.tenant_management_systemt.dto.Status;
import com.students.tenant_management_systemt.dto.UniversalResponse;
import com.students.tenant_management_systemt.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Slf4j
public class TenantOnboardingController {

    private final BookingService bookingService;

    @GetMapping("/houses")
    public ResponseEntity<UniversalResponse> getAllHouses() {
        log.info("Fetching all houses");
        UniversalResponse response = bookingService.getAllHouses();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/houses/{id}")
    public ResponseEntity<UniversalResponse> getHouseById(@PathVariable Long id) {
        log.info("Fetching house with ID: {}", id);
        UniversalResponse response = bookingService.getHouseById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/houses")
    public ResponseEntity<UniversalResponse> createHouse(@RequestBody @Validated HouseDto houseDto) {
        log.info("Creating new house: {}", houseDto.name());
        UniversalResponse response = bookingService.createHouse(houseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/houses/{id}")
    public ResponseEntity<UniversalResponse> updateHouse(@PathVariable Long id,
                                                         @RequestBody @Validated HouseDto houseDto) {
        log.info("Updating house with ID: {}", id);
        UniversalResponse response = bookingService.updateHouse(id, houseDto);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/houses/{id}")
    public ResponseEntity<UniversalResponse> deleteHouse(@PathVariable Long id) {
        log.info("Deleting house with ID: {}", id);
        UniversalResponse response = bookingService.deleteHouse(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Update house availability
     */
    @PatchMapping("/houses/{id}/availability")
    public ResponseEntity<UniversalResponse> updateHouseAvailability(@PathVariable Long id,
                                                                     @RequestParam boolean available) {
        log.info("Updating availability for house ID: {} to {}", id, available);
        UniversalResponse response = bookingService.updateHouseAvailability(id, available);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/houses/available")
    public ResponseEntity<UniversalResponse> getAvailableHouses() {
        log.info("Fetching available houses");
        UniversalResponse response = bookingService.getAvailableHouses();
        return ResponseEntity.ok(response);
    }

    /**
     * Search houses by location
     */
    @GetMapping("/houses/search/location")
    public ResponseEntity<UniversalResponse> searchHousesByLocation(@RequestParam String location) {
        log.info("Searching houses by location: {}", location);
        UniversalResponse response = bookingService.searchHousesByLocation(location);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/houses/search/price")
    public ResponseEntity<UniversalResponse> searchHousesByPriceRange(@RequestParam Double minPrice,
                                                                      @RequestParam Double maxPrice) {
        log.info("Searching houses by price range: {} - {}", minPrice, maxPrice);
        UniversalResponse response = bookingService.searchHousesByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/bookings")
    public ResponseEntity<UniversalResponse> getAllBookings() {
        log.info("Fetching all bookings");
        UniversalResponse response = bookingService.getAllBookings();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/bookings/{id}")
    public ResponseEntity<UniversalResponse> getBookingById(@PathVariable Long id) {
        log.info("Fetching booking with ID: {}", id);
        UniversalResponse response = bookingService.getBookingById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Create a new booking
     */
    @PostMapping("/bookings")
    public ResponseEntity<UniversalResponse> createBooking(@RequestBody @Validated BookingDto bookingDto) {
        log.info("Creating new booking for house ID: {}", bookingDto.houseId());
        UniversalResponse response = bookingService.createBooking(bookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PatchMapping("/bookings/{id}/status")
    public ResponseEntity<UniversalResponse> updateBookingStatus(@PathVariable Long id,
                                                                 @RequestParam Status status) {
        log.info("Updating booking status for ID: {} to {}", id, status);
        UniversalResponse response = bookingService.updateBookingStatus(id, status);
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/bookings/{id}/cancel")
    public ResponseEntity<UniversalResponse> cancelBooking(@PathVariable Long id) {
        log.info("Cancelling booking with ID: {}", id);
        UniversalResponse response = bookingService.cancelBooking(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/bookings/reference/{reference}")
    public ResponseEntity<UniversalResponse> getBookingByReference(@PathVariable String reference) {
        log.info("Fetching booking with reference: {}", reference);
        UniversalResponse response = bookingService.getBookingByReference(reference);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookings/tenant/{email}")
    public ResponseEntity<UniversalResponse> getBookingsByTenantEmail(@PathVariable String email) {
        log.info("Fetching bookings for tenant email: {}", email);
        UniversalResponse response = bookingService.getBookingsByTenantEmail(email);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/bookings/house/{houseId}")
    public ResponseEntity<UniversalResponse> getBookingsByHouseId(@PathVariable Long houseId) {
        log.info("Fetching bookings for house ID: {}", houseId);
        UniversalResponse response = bookingService.getBookingsByHouseId(houseId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/bookings/status/{status}")
    public ResponseEntity<UniversalResponse> getBookingsByStatus(@PathVariable Status status) {
        log.info("Fetching bookings with status: {}", status);
        UniversalResponse response = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookings/date-range")
    public ResponseEntity<UniversalResponse> getBookingsByDateRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("Fetching bookings between {} and {}", startDate, endDate);
        UniversalResponse response = bookingService.getBookingsByDateRange(startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<UniversalResponse> deleteBooking(@PathVariable Long id) {
        log.info("Deleting booking with ID: {}", id);
        UniversalResponse response = bookingService.deleteBooking(id);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/payments/process")
    public ResponseEntity<UniversalResponse> processPayment(@RequestBody @Validated PaymentDto paymentDto) {
        log.info("Processing payment for booking reference: {}", paymentDto.bookingReference());
        UniversalResponse response = bookingService.processPayment(paymentDto);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/payments/verify")
    public ResponseEntity<UniversalResponse> verifyPayment(@RequestParam String transactionId) {
        log.info("Verifying payment with transaction ID: {}", transactionId);

        // Mock verification logic
        Map<String, Object> verificationResult = Map.of(
                "transactionId", transactionId,
                "status", "VERIFIED",
                "amount", 100.0,
                "verificationTime", LocalDateTime.now()
        );

        UniversalResponse response = UniversalResponse.builder()
                .status(200)
                .message("Payment verified successfully")
                .data(verificationResult)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistics/bookings")
    public ResponseEntity<UniversalResponse> getBookingStatistics() {
        log.info("Fetching booking statistics");
        UniversalResponse response = bookingService.getBookingStatistics();
        return ResponseEntity.ok(response);
    }

}