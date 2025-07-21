package com.Auction.Platform.Service;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Auction.Platform.Dto.AuctionDetailsDTO;
import com.Auction.Platform.Dto.AuctionProductDTO;
import com.Auction.Platform.Dto.BidDTO;
import com.Auction.Platform.Entity.AuctionProduct;
import com.Auction.Platform.Entity.AuctionSubscription;
import com.Auction.Platform.Entity.Bid;
import com.Auction.Platform.Entity.User;
import com.Auction.Platform.Repository.AuctionProductRepository;
import com.Auction.Platform.Repository.AuctionSubscriptionRepository;
import com.Auction.Platform.Repository.BidRepository;
import com.Auction.Platform.Repository.UserRepository;
import com.google.api.client.util.Value;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class AuctionProductServiceImpl implements AuctionProductService {

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Autowired
	private Environment env;

	@PostConstruct
	public void logUploadDir() {
	    System.out.println("From env: " + env.getProperty("file.upload-dir"));
	}

	
    @Autowired
    private AuctionProductRepository productRepository;
    
    @Autowired
    private BidRepository bidRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    AuctionSubscriptionRepository subscriptionRepository;

    @Override
    public AuctionProduct addProduct(
            String name,
            double startingBid,
            String status,
            String endTime,
            MultipartFile imageFile,
            UUID sellerId,String duration
    ) throws IOException {
        // Generate unique image filename
        String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        // Create directory if not exists
        File directory = new File(env.getProperty("file.upload-dir"));
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save image file
        File saveFile = new File(uploadDir + filename);
        imageFile.transferTo(saveFile);

        // Create new product
        AuctionProduct product = new AuctionProduct();
        product.setName(name);
        product.setStartingBid(startingBid);
        product.setCurrentBid(startingBid);
        product.setStatus(status);
        product.setEndTime(LocalDateTime.parse(endTime));
        product.setImagePath(filename); // only store filename
        product.setDuration(duration);

        if ("Live".equalsIgnoreCase(status)) {
            product.setStartTime(LocalDateTime.now());
        }

        // Associate seller
        User seller = new User();
        seller.setUserId(sellerId);
        product.setSeller(seller);

        return productRepository.save(product);
    }

	@Override
    public List<AuctionProductDTO> getAllProducts(UUID userId) {
//         productRepository.findAll();	
         List<AuctionProduct> products = productRepository.findAll();
         return products.stream().map(product -> {
             AuctionProductDTO dto = new AuctionProductDTO();
             dto.setAuctionId(product.getId());
             dto.setProductName(product.getName());
             dto.setFinalPrice(product.getStartingBid());
             dto.setStatus(product.getStatus());
             dto.setSubscribed(checkSubscription(product.getId(),userId));
             dto.setStartDate(product.getStartTime());
             dto.setEndDate(product.getEndTime());
             dto.setProductImage(product.getImagePath());
             dto.setAuctionName(product.getAuctionName());
             dto.setDuration(product.getDuration());
             //fetching subscribe
            
             
             List<BidDTO> lastThreeBids = product.getBids().stream()
            	.filter(bid -> bid.getBidTime() != null)
                 .sorted(Comparator.comparing(Bid::getBidTime).reversed())
                 .limit(3)
                 .map(bid -> {
                     BidDTO bidDTO = new BidDTO();
                     bidDTO.setAmount(bid.getAmount());
                     bidDTO.setUser(bid.getBidder().getName()); // or userId
                     bidDTO.setTime(bid.getBidTime());
                     bidDTO.setId(bid.getId());
                     return bidDTO;
                 })
                 .collect(Collectors.toList());

             dto.setBidHistory(lastThreeBids);
             return dto;
         }).collect(Collectors.toList());
    }
	
	@Override
	public AuctionDetailsDTO getAuctionDetails(UUID id) throws Exception {
		try {
			AuctionProduct product = productRepository.findById(id).orElseThrow();
			List<Bid> bids = bidRepository.findByAuctionIdOrderByBidTimeAsc(id);
			
			List<BidDTO> bidDTOs = bids.stream().map(bid -> {
				BidDTO b = new BidDTO();
				b.setAmount(bid.getAmount());
				b.setUser(bid.getBidder().getName()); // assuming join with User
				b.setTime(bid.getBidTime());
				return b;
			}).collect(Collectors.toList());
			
			List<String> participants = bids.stream()
					.map(b -> b.getBidder().getName())
					.distinct()
					.collect(Collectors.toList());
			
			AuctionDetailsDTO dto = new AuctionDetailsDTO();
			dto.setId(product.getId());
			dto.setName(product.getName());
			dto.setCurrentBid(product.getCurrentBid());
			dto.setTimeRemaining(Duration.between(LocalDateTime.now(), product.getEndTime()).toString());
			dto.setStatus(product.getStatus());
			dto.setImage(product.getImagePath());
			dto.setBids(bidDTOs);
			dto.setParticipants(participants);
			dto.setDuration(product.getDuration());
			return dto;
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	@Transactional
    public Boolean subscribe(UUID productId, UUID userId) {
        Optional<AuctionSubscription> existing = subscriptionRepository
                .findByProductUser(productId, userId);
        Boolean isBoolean=null;
        
        if (existing.isPresent()) {
            AuctionSubscription subscription = existing.get();
            if (subscription.isSubscribed()) {
            	isBoolean=false;
            	subscription.setSubscribed(false);
            	subscription.setUnsubscribedAt(LocalDateTime.now());
            	subscriptionRepository.save(subscription);
                
            } else {
            	isBoolean=true;
                subscription.setSubscribed(true);
                subscription.setSubscribedAt(LocalDateTime.now());
                subscriptionRepository.save(subscription);
            }
        }
        else {
        	Optional<User> user=userRepository.findById(userId);
        	Optional<AuctionProduct>product= productRepository.findById(productId);
        	AuctionSubscription subscription= new AuctionSubscription();
        	isBoolean=true;
        	subscription.setProduct(product.get());
        	subscription.setUser(user.get());
            subscription.setSubscribed(true);
            subscription.setSubscribedAt(LocalDateTime.now());
            subscriptionRepository.save(subscription);
        }
        return isBoolean;
    }
	
	public Boolean checkSubscription(UUID productId,UUID userId) {
        Optional<AuctionSubscription> existing = subscriptionRepository
                .findByProductUser(productId, userId);
		 if (existing.isPresent()) {
			 return  existing.get().isSubscribed();
		 }
		 return false;
	}

	@Override
    @Transactional
    public String unsubscribe(UUID productId, UUID userId) {
        AuctionSubscription subscription = subscriptionRepository
                .findByProductUser(productId, userId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (!subscription.isSubscribed()) {
            return "Already unsubscribed";
        }

        subscription.setSubscribed(false);
        subscription.setUnsubscribedAt(LocalDateTime.now());
        subscriptionRepository.save(subscription);
        return "Unsubscribed successfully";
    }
}
