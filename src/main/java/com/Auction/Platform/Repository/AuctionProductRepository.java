package com.Auction.Platform.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Auction.Platform.Entity.AuctionProduct;

@Repository
public interface AuctionProductRepository extends JpaRepository<AuctionProduct, UUID> {
    // Add custom query methods here if needed in future
	
	AuctionProduct getById(UUID id);

	@Query(value="select product_id  from auction_subscriptions as2 where as2.id in (:auctionId)",nativeQuery=true)
	UUID getProductIdByAcutionId(@Param("auctionId")UUID auctionId);
}
