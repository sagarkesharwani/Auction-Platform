package com.Auction.Platform.Dto;

import java.util.UUID;

import lombok.Data;
@Data
public class JoinRequestDTO {
    private UUID auctionId;
    private UUID user;
    private UUID sessionId;

    // Getters and setters
    public UUID getAuctionId() { return auctionId; }
    public void setAuctionId(UUID auctionId) { this.auctionId = auctionId; }

    public UUID getSessionId() { return sessionId; }
    public void setSessionId(UUID sessionId) { this.sessionId = sessionId; }

    
    public UUID getUser() { return user; }
    public void setUser(UUID user) { this.user = user; }
}
