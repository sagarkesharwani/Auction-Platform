package com.Auction.Platform.Dto;

import java.util.UUID;

import lombok.Data;

@Data
public class SubscriptionResponseDTO {
    private UUID productId;
    private boolean subscribed;
    private UUID userId;
}

