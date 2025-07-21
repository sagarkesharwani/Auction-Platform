package com.Auction.Platform.Dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Data;
@Data
public class AuctionProductDTO {
    private UUID auctionId;
    private String productName;
    private Double finalPrice;
    private String status;
    private boolean subscribed;
    private List<BidDTO> bidHistory;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String productImage;
    private String auctionName;
    private String duration;
}
