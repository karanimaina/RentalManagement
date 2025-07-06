package com.students.tenant_management_systemt.service;

import com.students.tenant_management_systemt.dto.HouseDto;
import com.students.tenant_management_systemt.dto.BookingDto;
import com.students.tenant_management_systemt.dto.PaymentDto;
import com.students.tenant_management_systemt.dto.Status;
import com.students.tenant_management_systemt.dto.UniversalResponse;
import com.students.tenant_management_systemt.exception.TenantException;
import com.students.tenant_management_systemt.models.Booking;
import com.students.tenant_management_systemt.models.House;
import com.students.tenant_management_systemt.repository.BookingRepository;
import com.students.tenant_management_systemt.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final HouseRepository houseRepository;
    private final BookingRepository bookingRepository;
    public UniversalResponse getAllHouses() {
        List<House> list = houseRepository.findAll();
        if (list.isEmpty()) {
            return UniversalResponse.builder()
                    .status(200)
                    .message("No houses found")
                    .build();
        }
        return UniversalResponse.builder()
                .status(200)
                .message("Houses retrieved successfully")
                .data(list)
                .build();
    }
    public UniversalResponse getHouseById(Long id) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No house with the given id found"));
        return UniversalResponse.builder()
                .status(200)
                .message("House retrieved successfully")
                .data(house)
                .build();
    }

    public UniversalResponse createHouse(HouseDto houseDto) {
        House house = House.builder()
                .name(houseDto.name())
                .location(houseDto.location())
                .description(houseDto.description())
                .pricePerDay(houseDto.pricePerDay())
                .imageUrl(houseDto.imageUrl())
                .available(true)
                .build();

        House savedHouse = houseRepository.save(house);
        return UniversalResponse.builder()
                .status(201)
                .message("House created successfully")
                .data(savedHouse)
                .build();
    }

    public UniversalResponse updateHouse(Long id, HouseDto houseDto) {
        House existingHouse = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found with id: " + id));
        existingHouse.setName(houseDto.name());
        existingHouse.setLocation(houseDto.location());
        existingHouse.setDescription(houseDto.description());
        existingHouse.setPricePerDay(houseDto.pricePerDay());
        existingHouse.setImageUrl(houseDto.imageUrl());

        House updatedHouse = houseRepository.save(existingHouse);
        return UniversalResponse.builder()
                .status(200)
                .message("House updated successfully")
                .data(updatedHouse)
                .build();
    }

    public UniversalResponse deleteHouse(Long id) {
        if (!houseRepository.existsById(id)) {
            throw new RuntimeException("House not found with id: " + id);
        }
        List<Booking> activeBookings = bookingRepository.findByHouseIdAndStatus(id, Status.BOOKED);
        if (!activeBookings.isEmpty()) {
            return UniversalResponse.builder()
                    .status(400)
                    .message("Cannot delete house with active bookings")
                    .build();
        }
        houseRepository.deleteById(id);
        return UniversalResponse.builder()
                .status(200)
                .message("House deleted successfully")
                .build();
    }
    public UniversalResponse updateHouseAvailability(Long id, boolean available) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found with id: " + id));
        house.setAvailable(available);
        House updatedHouse = houseRepository.save(house);
        return UniversalResponse.builder()
                .status(200)
                .message("House availability updated successfully")
                .data(updatedHouse)
                .build();
    }
    public UniversalResponse getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty()) {
            return UniversalResponse.builder()
                    .status(200)
                    .message("No bookings found")
                    .build();
        }
        return UniversalResponse.builder()
                .status(200)
                .message("Bookings retrieved successfully")
                .data(bookings)
                .build();
    }

    /**
     * Get booking by ID
     */
    public UniversalResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new TenantException("No booking found with id: " + id));
        return UniversalResponse.builder()
                .status(200)
                .message("Booking retrieved successfully")
                .data(booking)
                .build();
    }

    /**
     * Create a new booking
     */
    public UniversalResponse createBooking(BookingDto request) {
        House house = houseRepository.findById(request.houseId())
                .orElseThrow(() -> new RuntimeException("House not found"));

        if (!house.isAvailable()) {
            return UniversalResponse.builder()
                    .status(400)
                    .message("House is not available for booking")
                    .build();
        }

        double totalAmount = house.getPricePerDay() * request.numberOfDays();
        String bookingReference;
        String houseCode;

        // Generate unique booking reference
        do {
            bookingReference = generateBookingReference();
        } while (bookingRepository.existsByBookingReference(bookingReference));

        // Generate unique house code
        do {
            houseCode = generateHouseCode();
        } while (bookingRepository.existsByHouseCode(houseCode));

        Booking booking = Booking.builder()
                .bookingDateTime(LocalDateTime.now())
                .bookingReference(bookingReference)
                .status(Status.BOOKED)
                .houseCode(houseCode)
                .endDate(request.endDate())
                .startDate(request.startDate())
                .tenantEmail(request.tenantEmail())
                .totalAmount(totalAmount)
                .numberOfDays(request.numberOfDays())
                .tenantName(request.tenantName())
                .tenantPhone(request.tenantPhone())
                .houseId(request.houseId())
                .apartmentName(request.apartmentName())
                .build();

        Booking savedBooking = bookingRepository.save(booking);
        return UniversalResponse.builder()
                .status(201)
                .message("Booking created successfully")
                .data(savedBooking)
                .build();
    }
    public UniversalResponse updateBookingStatus(Long bookingId, Status status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new TenantException("No booking found with id: " + bookingId));
        booking.setStatus(status);
        Booking updatedBooking = bookingRepository.save(booking);
        return UniversalResponse.builder()
                .status(200)
                .message("Booking status updated successfully")
                .data(updatedBooking)
                .build();
    }
    public UniversalResponse cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new TenantException("No booking found with id: " + bookingId));
        if (booking.getStatus() == Status.CANCELLED) {
            return UniversalResponse.builder()
                    .status(400)
                    .message("Booking is already cancelled")
                    .build();
        }

        booking.setStatus(Status.CANCELLED);
        Booking cancelledBooking = bookingRepository.save(booking);

        return UniversalResponse.builder()
                .status(200)
                .message("Booking cancelled successfully")
                .data(cancelledBooking)
                .build();
    }

    /**
     * Get booking by reference
     */
    public UniversalResponse getBookingByReference(String reference) {
        Booking booking = bookingRepository.findByBookingReference(reference)
                .orElseThrow(() -> new TenantException("No booking found with reference: " + reference));

        return UniversalResponse.builder()
                .status(200)
                .message("Booking retrieved successfully")
                .data(booking)
                .build();
    }

    /**
     * Get bookings by tenant email
     */
    public UniversalResponse getBookingsByTenantEmail(String email) {
        List<Booking> bookings = bookingRepository.findByTenantEmail(email);
        if (bookings.isEmpty()) {
            return UniversalResponse.builder()
                    .status(200)
                    .message("No bookings found for tenant: " + email)
                    .build();
        }

        return UniversalResponse.builder()
                .status(200)
                .message("Bookings retrieved successfully")
                .data(bookings)
                .build();
    }

    /**
     * Get bookings by house ID
     */
    public UniversalResponse getBookingsByHouseId(Long houseId) {
        List<Booking> bookings = bookingRepository.findByHouseId(houseId);
        if (bookings.isEmpty()) {
            return UniversalResponse.builder()
                    .status(200)
                    .message("No bookings found for house ID: " + houseId)
                    .build();
        }
        return UniversalResponse.builder()
                .status(200)
                .message("Bookings retrieved successfully")
                .data(bookings)
                .build();
    }

    /**
     * Get bookings by status
     */
    public UniversalResponse getBookingsByStatus(Status status) {
        List<Booking> bookings = bookingRepository.findByStatus(status);
        if (bookings.isEmpty()) {
            return UniversalResponse.builder()
                    .status(200)
                    .message("No bookings found with status: " + status)
                    .build();
        }
        return UniversalResponse.builder()
                .status(200)
                .message("Bookings retrieved successfully")
                .data(bookings)
                .build();
    }
    public UniversalResponse getBookingsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.findByStartDateBetween(startDate, endDate);
        if (bookings.isEmpty()) {
            return UniversalResponse.builder()
                    .status(200)
                    .message("No bookings found in the specified date range")
                    .build();
        }
        return UniversalResponse.builder()
                .status(200)
                .message("Bookings retrieved successfully")
                .data(bookings)
                .build();
    }


    public UniversalResponse deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new TenantException("No booking found with id: " + bookingId));
        booking.setStatus(Status.DELETED);
        booking.setSoftDelete(false);
        bookingRepository.save(booking);
        return UniversalResponse.builder()
                .status(200)
                .message("Booking deleted successfully")
                .build();
    }

    // ===================== UTILITY METHODS =====================

    /**
     * Generate unique house code
     */
    private String generateHouseCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }

    /**
     * Generate unique booking reference
     */
    private String generateBookingReference() {
        String chars = "0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }

    /**
     * Mock payment processing
     */
    public UniversalResponse processPayment(PaymentDto paymentDto) {
        // Mock payment logic
        boolean paymentSuccess = Math.random() > 0.1; // 90% success rate

        if (paymentSuccess) {
            return UniversalResponse.builder()
                    .status(200)
                    .message("Payment processed successfully")
                    .data(Map.of(
                            "transactionId", "TXN" + System.currentTimeMillis(),
                            "amount", paymentDto.amount(),
                            "status", "SUCCESS"
                    ))
                    .build();
        } else {
            return UniversalResponse.builder()
                    .status(400)
                    .message("Payment failed")
                    .data(Map.of("status", "FAILED"))
                    .build();
        }
    }

    /**
     * Get booking statistics
     */
    public UniversalResponse getBookingStatistics() {
        long totalBookings = bookingRepository.count();
        long activeBookings = bookingRepository.countByStatus(Status.BOOKED);
        long cancelledBookings = bookingRepository.countByStatus(Status.CANCELLED);
        long completedBookings = bookingRepository.countByStatus(Status.COMPLETED);

        Map<String, Object> statistics = Map.of(
                "totalBookings", totalBookings,
                "activeBookings", activeBookings,
                "cancelledBookings", cancelledBookings,
                "completedBookings", completedBookings
        );
        return UniversalResponse.builder()
                .status(200)
                .message("Statistics retrieved successfully")
                .data(statistics)
                .build();
    }

    public UniversalResponse getAvailableHouses() {
        List<House> availableHouses = houseRepository.findByAvailableTrue();
        return  UniversalResponse.builder()
                .status(200)
                .message("available houses")
                .data(availableHouses)
                .build();
    }

    public UniversalResponse searchHousesByLocation(String location) {
        List<House>houses = houseRepository.findByLocationIgnoreCase(location);
        if (houses.isEmpty()) {
            return UniversalResponse.builder()
                    .status(200)
                    .message("No houses present in this location")
                    .build();
        }
        return UniversalResponse.builder()
                .status(200)
                .message("houses in this location")
                .data(houses)
                .build();
    }

    public UniversalResponse searchHousesByPriceRange(Double minPrice, Double maxPrice) {
        List<House>houses = houseRepository.findHouseByPricePerDayBetween(minPrice,maxPrice);
        if (houses.isEmpty()) {
            return UniversalResponse.builder()
                    .status(200)
                    .message("No houses present in this location")
                    .build();
        }
        return UniversalResponse.builder()
                .status(200)
                .message("houses in this location")
                .data(houses)
                .build();
    }
}