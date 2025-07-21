package com.Auction.Platform.Dto;

import lombok.Data;

@Data
public class BidMessage {
    private String productId;
    private String bidder;
    private Double amount;

    // Getters and setters
}
