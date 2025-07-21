package com.Auction.Platform.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Auction.Platform.Entity.AuctionProduct;

@Repository
public interface AuctionProductRepository extends JpaRepository<AuctionProduct, UUID> {
    // Add custom query methods here if needed in future
	
	AuctionProduct getById(UUID id);
}
