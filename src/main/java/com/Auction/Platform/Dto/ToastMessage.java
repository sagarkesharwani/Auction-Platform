package com.Auction.Platform.Dto;

import java.util.UUID;

public class ToastMessage {
    private String name;
    private UUID sessionId;
    private UUID userId;

    // ✅ Constructors
    public ToastMessage() {}
    public ToastMessage(String name, UUID sessionId) {
        this.name = name;
        this.sessionId = sessionId;
    }
    public ToastMessage(UUID tempUserId, UUID sessionId,String name) {
        this.userId = tempUserId;
        this.sessionId = sessionId;
        this.name=name;
    }

    // ✅ Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public UUID getSessionId() { return sessionId; }
    public void setSessionId(UUID sessionId) { this.sessionId = sessionId; }
}
