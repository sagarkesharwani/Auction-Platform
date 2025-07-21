package com.Auction.Platform.Dto;

import java.util.UUID;

import lombok.Data;

@Data
public class SubscriptionResponse {
	private UUID productId;
    private boolean subscribed;
    public SubscriptionResponse(UUID productId2, Boolean isSubscribed) {
    	this.productId=productId2;
    	this.subscribed=isSubscribed;
    }
}

