package com.Auction.Platform.Service;

import java.util.UUID;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuctionWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public AuctionWebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

}