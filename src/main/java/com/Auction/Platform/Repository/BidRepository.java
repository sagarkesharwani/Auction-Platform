package com.Auction.Platform.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Auction.Platform.Entity.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, UUID> {
    // Add custom query methods here if needed in future
	
	  List<Bid> findByAuctionIdOrderByBidTimeAsc(UUID auctionId);

	List<Bid> findByAuctionIdOrderByAmountDesc(UUID auctionId);
}
