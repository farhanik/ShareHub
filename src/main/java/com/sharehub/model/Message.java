package com.sharehub.model;

import java.time.LocalDateTime;

public class Message {
    private Long id;
    private Long senderId;     // ID of the sender (populated by frontend)
    private Long receiverId;
    private User sender;
    private User receiver;
    private String content;
    private LocalDateTime timestamp;
    private boolean read;
    private Booking relatedBooking;

    public Message() {
        this.timestamp = LocalDateTime.now();
        this.read = false;
    }

    public Message(User sender, User receiver, String content) {
        this();
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    // Getters and Setters

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }


    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Booking getRelatedBooking() {
        return relatedBooking;
    }

    public void setRelatedBooking(Booking relatedBooking) {
        this.relatedBooking = relatedBooking;
    }

}