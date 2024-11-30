package com.sharehub.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Tool {
    private Long id;
    private String name;
    private String description;
    private BigDecimal rentalRate;
    private User owner;
    private boolean available;
    private Set<Booking> bookings;
    private String category;
    private String condition;

    public Tool() {
        this.bookings = new HashSet<>();
        this.available = true;
    }

    public Tool(String name, String description, BigDecimal rentalRate, User owner) {
        this();
        this.name = name;
        this.description = description;
        this.rentalRate = rentalRate;
        this.owner = owner;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }


    // Helper methods
    public void addBooking(Booking booking)
    {
        bookings.add(booking);
        booking.setTool(this);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setTool(null);
    }
}