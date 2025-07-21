package com.Auction.Platform.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.Auction.Platform.Dto.AuctionDetailsDTO;
import com.Auction.Platform.Dto.AuctionProductDTO;
import com.Auction.Platform.Entity.AuctionProduct;

public interface AuctionProductService {

	AuctionProduct addProduct(String name, double startingBid, String status, String endTime, MultipartFile imageFile,
			UUID sellerId,String duration) throws IOException;

	List<AuctionProductDTO> getAllProducts(UUID userId);

	AuctionDetailsDTO getAuctionDetails(UUID id) throws Exception;

	Boolean subscribe(UUID productId, UUID userId);

	String unsubscribe(UUID productId, UUID userId);
}
