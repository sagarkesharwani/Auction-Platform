package com.Auction.Platform.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "auction_products")
public class AuctionProduct {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;


    private String name;

    private double startingBid;
    
    private String status; // e.g., "Live", "Upcoming", "Ended"

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String imagePath; // Only file name (e.g., "macbook.png")

    private double currentBid;
    
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private String auctionName;
    
    private String duration;
    
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Bid> bids;

    public AuctionProduct() {}

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(double startingBid) {
        this.startingBid = startingBid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }

	public void setSeller(User userId) {
		this.seller=userId;
	}
	
	public List<Bid> getBids() {
		return bids;
	}
	
    public String getAuctionName() {
        return this.auctionName;
    }
    
    public void setAuctionName(String name) {
        this.auctionName=name;
    }

	public void setDuration(String duration) {
		// TODO Auto-generated method stub
		this.duration=duration;
	}
	
    public String getDuration() {
        return duration;
    }
	
}
