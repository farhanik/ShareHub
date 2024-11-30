package com.sharehub.service.impl;

import com.sharehub.model.Booking;
import com.sharehub.service.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    private Map<Long, Booking> bookingDatabase = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public Booking createBooking(Booking booking) {
        // Validate booking dates
        if (booking.getStartDate().isAfter(booking.getEndDate()))
        {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        // Check tool availability. COUNTER BASE AVAIL IMPLEMENT KORTE HOBE
        if (!isToolAvailable(booking.getToolId(),
                booking.getStartDate(),
                booking.getEndDate()))
        {
            throw new IllegalArgumentException("Tool is not available for the selected dates");
        }

        booking.setId(currentId++);
        bookingDatabase.put(booking.getId(), booking);
        return booking;
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return Optional.ofNullable(bookingDatabase.get(id));
    }

    @Override
    public List<Booking> getBookingsByBorrower(Long borrowerId)
    {
        List<Booking> result = new ArrayList<>(); // Create a list to store the matching bookings

        // Iterate over all bookings in the bookingDatabase
        for (Booking booking : bookingDatabase.values())
        {
            // Check if the borrower ID matches
            if (booking.getBorrower().getId().equals(borrowerId))
            {
                result.add(booking); // Add matching booking to the result list
            }
        }

        return result; // Return the filtered list of bookings
    }


    @Override
    //Retrieves all bookings associated with a specific tool.
    public List<Booking> getBookingsByTool(Long toolId)
    {
        return bookingDatabase.values().stream()
                .filter(booking -> booking.getTool().getId().equals(toolId))
                .collect(Collectors.toList());
    }

    @Override
    public Booking updateBookingStatus(Long bookingId, Booking.BookingStatus status)
    {
        Booking booking = bookingDatabase.get(bookingId);
        if (booking == null)
        {
            throw new IllegalArgumentException("Booking not found");
        }
        booking.setStatus(status);
        return booking;
    }

    @Override
    public void cancelBooking(Long bookingId)
    {
        Booking booking = bookingDatabase.get(bookingId);
        if (booking != null)
        {
            booking.setStatus(Booking.BookingStatus.CANCELLED);
        }
    }

    @Override
    public boolean isToolAvailable(Long toolId, LocalDateTime startDate, LocalDateTime endDate)
    {
        if (startDate == null || endDate == null || !startDate.isBefore(endDate))
        {
            throw new IllegalArgumentException("Invalid date range");
        }

        return bookingDatabase.values().stream()
                .filter(booking -> booking.getTool().getId().equals(toolId))
                .filter(booking -> booking.getStatus() == Booking.BookingStatus.CONFIRMED)
                .noneMatch(booking ->
                        (startDate.isBefore(booking.getEndDate()) &&
                                endDate.isAfter(booking.getStartDate())));
    }

    @Override
    public List<Booking> getActiveBookings()
    {
        return bookingDatabase.values().stream()
                .filter(Booking::isActive)
                .collect(Collectors.toList());
    }
}