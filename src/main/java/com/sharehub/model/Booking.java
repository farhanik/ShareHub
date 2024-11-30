package com.sharehub.model;

import java.time.LocalDateTime;

public class Booking {
    private Long id;
    private User borrower;
    private Tool tool;
    private Long toolId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BookingStatus status;
    private String notes;

    public enum BookingStatus
    {
        PENDING,
        CONFIRMED,
        COMPLETED,
        CANCELLED
    }

    public Booking() {
        this.status = BookingStatus.PENDING;
    }

    public Booking(User borrower, Tool tool, LocalDateTime startDate, LocalDateTime endDate) {
        this();
        this.borrower = borrower;
        this.tool = tool;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters

    public void setToolId(Long toolId)
    {
        this.toolId = toolId;
    }

    public Long getToolId()
    {
        return toolId;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }


    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    // Helper methods
    public boolean isActive()
    {
        return status == BookingStatus.CONFIRMED &&
                LocalDateTime.now().isAfter(startDate) &&
                LocalDateTime.now().isBefore(endDate);
    }
}