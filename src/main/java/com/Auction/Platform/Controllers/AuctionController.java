package com.Auction.Platform.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Auction.Platform.Dto.AuctionDetailsDTO;
import com.Auction.Platform.Dto.AuctionProductDTO;
import com.Auction.Platform.Entity.AuctionProduct;
import com.Auction.Platform.Service.AuctionProductService;
import com.Auction.Platform.Service.AuctionSessionService;

@RestController
@RequestMapping("api/")
public class AuctionController {

	@Autowired
	AuctionProductService	auctionProductService;
	
	@Autowired
	AuctionSessionService auctionSessionService;
	
	@PostMapping("/auction-products")
	public ResponseEntity<AuctionProduct> addProduct(
	        @RequestParam("name") String name,
	        @RequestParam("startingBid") double startingBid,
	        @RequestParam("status") String status,
	        @RequestParam("endTime") String endTime,
	        @RequestParam("image") MultipartFile imageFile,
	        @RequestParam("sellerId") UUID sellerId,
	        @RequestParam("duration") String duration
	) {
	    try {
	        // Pass params to service, let it handle everything
	    	AuctionProduct productDetails=auctionProductService.addProduct(name, startingBid, status, endTime, imageFile, sellerId,duration);
	        return ResponseEntity.ok(productDetails);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

    @GetMapping("/auction-products")
    public List<AuctionProductDTO> getAllAuctionProducts(@RequestParam("userId") UUID userId) {
        return auctionProductService.getAllProducts(userId);
    }
	
    @GetMapping("/auction/{id}")
    public ResponseEntity<AuctionDetailsDTO> getAuctionDetails(@PathVariable UUID id) {
    	try {
    	AuctionDetailsDTO details=auctionProductService.getAuctionDetails(id);
    	return ResponseEntity.ok(details);
    	} catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
    }
    
    
    @PostMapping("auction/start/{auctionId}")
    public String startAuction(@PathVariable UUID auctionId) {
    	auctionSessionService.startAuction(auctionId);
        return "Auction started for 15 minutes.";
    }
}
