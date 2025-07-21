package com.Auction.Platform.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Auction.Platform.Dto.BidDetailsDto;
import com.Auction.Platform.Entity.AuctionProduct;
import com.Auction.Platform.Entity.Bid;
import com.Auction.Platform.Entity.User;
import com.Auction.Platform.Repository.AuctionProductRepository;
import com.Auction.Platform.Repository.BidRepository;
import com.Auction.Platform.Repository.UserRepository;

@Controller
public class BidController {

    private final BidRepository bidRepository;
    private final SimpMessagingTemplate messagingTemplate;
    
    
    @Autowired
    AuctionProductRepository auctionProductRepository;
    
    @Autowired
    UserRepository userRepository;

    public BidController(BidRepository bidRepository, SimpMessagingTemplate messagingTemplate) {
        this.bidRepository = bidRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/auction/{auctionId}/bid")
    public void placeBid(@Payload BidDetailsDto bidDetails) {
    	Bid bid = new Bid();
    	AuctionProduct auctionProduct=null;
    	Optional<AuctionProduct> product= auctionProductRepository.findById(bidDetails.getAuctionId());
    	if(!product.isEmpty()) {
    		auctionProduct=product.get();
    	}
    	User user= userRepository.findByUserId(bidDetails.getUserId());
    	
    	
        bid.setAuction(auctionProduct);
        bid.setBidTime(bidDetails.getBidTime());
        bid.setAmount(bidDetails.getAmount());
        bid.setBidder(user);

        // Save to DB
        bidRepository.save(bid);

        // Broadcast to topic
        messagingTemplate.convertAndSend("/topic/auction/" + bidDetails.getAuctionId() + "/bids", bid);
    }
    
    @GetMapping("/api/auction/{auctionId}/bids")
    public ResponseEntity<List<BidDetailsDto>> getBidsForAuction(@PathVariable UUID auctionId) {
        List<Bid> bids = bidRepository.findByAuctionIdOrderByAmountDesc(auctionId);
        return ResponseEntity.ok(bids.stream()
                .map(bid -> new BidDetailsDto(
                    bid.getAmount(),
                    bid.getBidder().getUserId(),
                    bid.getBidTime(),  // Make sure bidTime is not null!
                    bid.getAuction().getId(),bid.getBidder().getName()
                ))
                .collect(Collectors.toList()));
//        return ResponseEntity.ok(bids);
    }

}
