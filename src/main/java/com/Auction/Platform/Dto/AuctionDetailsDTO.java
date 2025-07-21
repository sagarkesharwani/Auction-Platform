package com.Auction.Platform.Dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class AuctionDetailsDTO {
    private UUID id;
    private String name;
    private Double currentBid;
    private String timeRemaining;  // e.g. "PT10M" or parsed to string
    private String status;
    private String image;
    private List<BidDTO> bids;
    private List<String> participants;
    private String duration;
}
