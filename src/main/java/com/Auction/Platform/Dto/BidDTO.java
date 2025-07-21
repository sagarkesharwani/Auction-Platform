package com.Auction.Platform.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class BidDTO {
    private BigDecimal amount;
    private String user;
    private LocalDateTime time;
    private UUID id;
}
