package com.sharehub.controller;

import com.sharehub.model.Booking;
import com.sharehub.model.Tool;
import java.util.Optional;
import com.sharehub.service.BookingService;
import com.sharehub.service.ToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {
    private final BookingService bookingService;
    private final ToolService toolService;

    public BookingController(BookingService bookingService, ToolService toolService)
    {
        this.bookingService = bookingService;
        this.toolService = toolService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Booking>> createBooking(@RequestBody Booking booking)
    {
        try
        {
            System.out.println("Received Booking: " + booking);  // Debug line to check received data

            Optional<Tool> toolOptional = toolService.getToolById(booking.getToolId());
            if (toolOptional.isEmpty()) {
                throw new IllegalArgumentException("Tool not found for the given ID");
            }

            // Set the fetched tool to the booking
            booking.setTool(toolOptional.get());

            Booking createdBooking = bookingService.createBooking(booking);
            System.out.println("Booking successfully created: " + createdBooking); // Log success
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", createdBooking));
        } catch (IllegalArgumentException e) {
            System.out.println("Error during booking creation: " + e.getMessage()); // Log specific error
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }






    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Booking>> getBooking(@PathVariable Long id)
    {
        return bookingService.getBookingById(id)
                .map(booking -> ResponseEntity.ok(new ApiResponse<>(true, "Booking found", booking)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/borrower/{borrowerId}")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookingsByBorrower(@PathVariable Long borrowerId)
    {
        List<Booking> bookings = bookingService.getBookingsByBorrower(borrowerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Bookings retrieved successfully", bookings));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Booking>> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam Booking.BookingStatus status) {
        try {
            Booking updatedBooking = bookingService.updateBookingStatus(id, status);
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status updated", updatedBooking));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking cancelled successfully", null));
    }
}