package com.Auction.Platform.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Auction.Platform.Entity.AuctionProduct;
import com.Auction.Platform.Entity.AuctionSubscription;
import com.Auction.Platform.Entity.User;

public interface AuctionSubscriptionRepository extends JpaRepository<AuctionSubscription, UUID> {
	Optional<AuctionSubscription> findByProductAndUser(AuctionProduct product, User user);
	
	 @Query("SELECT s FROM AuctionSubscription s WHERE s.product.id = :productId AND s.user.userId = :userId")
	 Optional<AuctionSubscription> findByProductUser(UUID productId, UUID userId);

}
