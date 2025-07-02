package com.students.tenant_management_systemt.controller;

import com.students.tenant_management_systemt.models.House;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/houses")
    public List<House> getAllHouses() {
        return bookingService.getAllHouses();
    }

    @GetMapping("/houses/{id}")
    public House getHouseById(@PathVariable Long id) {
        return bookingService.getHouseById(id)
                .orElseThrow(() -> new RuntimeException("House not found"));
    }

    @PostMapping("/bookings")
    public Booking createBooking(@RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @GetMapping("/bookings/{reference}")
    public Booking getBookingByReference(@PathVariable String reference) {
        return bookingService.getBookingByReference(reference)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @PostMapping("/payment/mock")
    public Map<String, Object> mockPayment(@RequestBody Map<String, Object> paymentData) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("transactionId", "TXN" + System.currentTimeMillis());
        response.put("message", "Payment processed successfully");
        return response;
    }
