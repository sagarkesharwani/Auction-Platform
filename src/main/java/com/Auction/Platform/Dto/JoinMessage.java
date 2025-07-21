package com.Auction.Platform.Dto;

import java.util.UUID;

import lombok.Data;

@Data
public class JoinMessage {
    private UUID auctionId;
    private UUID userId;

    // Getters and setters
}
