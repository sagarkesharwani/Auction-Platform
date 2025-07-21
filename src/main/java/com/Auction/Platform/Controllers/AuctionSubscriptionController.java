package com.Auction.Platform.Controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Auction.Platform.Dto.SubscriptionResponse;
import com.Auction.Platform.Dto.SubscriptionResponseDTO;
import com.Auction.Platform.Service.AuctionProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class AuctionSubscriptionController {

	@Autowired
	AuctionProductService	auctionProductService;

	@PostMapping("/subscribe")
	public ResponseEntity<SubscriptionResponse> toggleSubscription(@RequestBody SubscriptionResponseDTO req) {
        Boolean isSubscribed = auctionProductService.subscribe(req.getProductId(), req.getUserId());
        return ResponseEntity.ok(new SubscriptionResponse(req.getProductId(), isSubscribed));
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(
            @RequestParam UUID productId,
            @RequestParam UUID userId) {
        String result = auctionProductService.unsubscribe(productId, userId);
        return ResponseEntity.ok(result);
    }
}
