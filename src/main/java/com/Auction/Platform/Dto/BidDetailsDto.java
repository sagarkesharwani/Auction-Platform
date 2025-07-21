package com.Auction.Platform.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BidDetailsDto {
    private BigDecimal amount;
    private UUID userId;
    private LocalDateTime bidTime;
    private UUID auctionId;
    private String name;
}
