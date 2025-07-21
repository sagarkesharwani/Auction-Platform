package com.Auction.Platform.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bids")
@Data
public class Bid {
	@Id
	@Column(name = "bidid", nullable = false, updatable = false)
	private UUID id = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auctionid")
//    @JsonIgnore
    private AuctionProduct auction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidderid")
//    @JsonIgnore
    private User bidder;

    private BigDecimal amount;

    @Column(name = "bidtime")
    private LocalDateTime bidTime;
    
    
//    @JsonProperty("auctionId")
//    public UUID getAuctionId() {
//        return auction != null ? auction.getId() : null;
//    }
//
//    @JsonProperty("bidderId")
//    public UUID getBidderId() {
//        return bidder != null ? bidder.getUserId() : null;
//    }

}
