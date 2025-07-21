package com.Auction.Platform.Controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.Auction.Platform.Dto.AuctionBid;
import com.Auction.Platform.Dto.JoinMessage;
import com.Auction.Platform.Dto.JoinRequestDTO;
import com.Auction.Platform.Dto.ToastMessage;
import com.Auction.Platform.Entity.User;
import com.Auction.Platform.Repository.UserRepository;
import com.Auction.Platform.Service.AuctionSessionService;

@RestController
public class AuctionWebSocketController {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;
  
  @Autowired
  private AuctionSessionService  auctionSessionService;
  
  @Autowired
  private UserRepository userRepository;

  
  private final Map<UUID, UUID> sessionIdToUserIdMap = new ConcurrentHashMap<>();

  private final Map<UUID, Map<UUID,User>> auctionRoomUsers = new ConcurrentHashMap<>();
  
  public AuctionWebSocketController(SimpMessagingTemplate messagingTemplate) {
      this.messagingTemplate = messagingTemplate;
  }
  @MessageMapping("/bid")  // /app/bid
  public void receiveBid(AuctionBid bid) {
    // Broadcast to everyone in that auction room
    messagingTemplate.convertAndSend("/topic/auction/" + bid.getAuctionId(), bid);
  }
  

//  @MessageMapping("/join")  // /app/join
//  public void userJoined(@Payload JoinRequestDTO request) {
//      UUID auctionId = request.getAuctionId();
//      UUID userId = request.getUser();
//      User userDetails=userRepository.findByUserId(userId);
//      // Add user to room
//      auctionRoomUsers
//      .computeIfAbsent(auctionId, k -> new ConcurrentHashMap<>())
//      .put(userId, userDetails);
//
//
//      // Notify all users in room
//      Map<UUID,User> users = auctionRoomUsers.get(auctionId);
//      messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/users", users);
//  }
    
  @MessageMapping("/user-joined")
  @SendTo("/topic/room-users")
  public Set<UUID> joinRoom(JoinMessage message, UUID sessionId) {
      return auctionSessionService.addUserAndGetRoomUsers(message.getAuctionId(), sessionId);
  }
  
  public Collection<User> getUsersInAuctionRoom(UUID auctionId) {
	    return auctionRoomUsers.getOrDefault(auctionId, Collections.emptyMap()).values();
	}
  
  @MessageMapping("/leave")
  public void userLeft(@Payload Map<String, String> payload) {
      UUID auctionId = UUID.fromString(payload.get("auctionId"));
      UUID userId = UUID.fromString(payload.get("userId"));

      Map<UUID, User> users = auctionRoomUsers.get(auctionId);
      if (users != null && !users.isEmpty()) {
    	    users.remove(userId);
    	    System.out.println("User left: " + userId);

    	    // Optional cleanup: remove the map if it's now empty
    	    if (users.isEmpty()) {
    	        auctionRoomUsers.remove(auctionId);
    	    }

    	    messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/users", users.values());
    	}
  }

  
  @MessageMapping("/join")
  public void userJoined(@Payload JoinRequestDTO request, SimpMessageHeaderAccessor headerAccessor) {
	  
      UUID auctionId = request.getAuctionId();
      UUID userId = request.getUser();
      User user=userRepository.findByUserId(userId);

      // Track the user in auction room
      auctionRoomUsers.computeIfAbsent(auctionId, k -> new ConcurrentHashMap<>())
          .put(userId, userRepository.findByUserId(userId)); // assuming you have a service

      // Save session ID
      UUID sessionId = request.getSessionId();
      sessionIdToUserIdMap.put(sessionId, userId);

//      // Send user list update
      messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/users", auctionRoomUsers.get(auctionId).values());

      // Send toast to others (excluding this session)
      messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/toast", new ToastMessage(userId, sessionId,user.getName()));
  }



}
