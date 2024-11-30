package com.sharehub.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private Set<Tool> toolsOwned;
    private Set<Booking> bookings;

    public User() {
        this.toolsOwned = new HashSet<>();
        this.bookings = new HashSet<>();
    }

    // Constructor with required fields
    public User(String username, String email, String password, String firstName, String lastName)
    {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Tool> getToolsOwned() {
        return toolsOwned;
    }

    public void setToolsOwned(Set<Tool> toolsOwned) {
        this.toolsOwned = toolsOwned;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    // Helper methods
    public void addTool(Tool tool)
    {
        toolsOwned.add(tool);
        tool.setOwner(this);
    }

    public void removeTool(Tool tool)
    {
        toolsOwned.remove(tool);
        tool.setOwner(null);
    }
}