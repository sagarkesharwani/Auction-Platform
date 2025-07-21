package com.Auction.Platform.Dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionBid {
  private String auctionId;
  private String user;
  private double amount;
  private LocalDateTime time;
}
