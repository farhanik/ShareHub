package com.sharehub.service;

import com.sharehub.model.Booking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface BookingService {
    Booking createBooking(Booking booking);
    Optional<Booking> getBookingById(Long id);
    List<Booking> getBookingsByBorrower(Long borrowerId);
    List<Booking> getBookingsByTool(Long toolId);
    Booking updateBookingStatus(Long bookingId, Booking.BookingStatus status);
    void cancelBooking(Long bookingId);
    boolean isToolAvailable(Long toolId, LocalDateTime startDate, LocalDateTime endDate);
    List<Booking> getActiveBookings();
}