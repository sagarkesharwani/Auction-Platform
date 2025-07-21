package com.Auction.Platform.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class AuctionSessionService {

    private final Map<UUID, Set<UUID>> auctionRoomUsers = new ConcurrentHashMap<>();

//    public void addUserToRoom(UUID auctionId, UUID username) {
//        auctionRoomUsers.computeIfPresent(auctionId, k -> ConcurrentHashMap.newKeySet()).add(username);
//    }
    public void addUserToRoom(UUID auctionId, UUID username) {
        auctionRoomUsers
            .computeIfAbsent(auctionId, k -> ConcurrentHashMap.newKeySet())
            .add(username);
    }


    public void removeUserFromRoom(UUID auctionId, UUID username) {
        Set<UUID> users = auctionRoomUsers.get(auctionId);
        if (users != null) {
            users.remove(username);
            if (users.isEmpty()) {
                auctionRoomUsers.remove(auctionId);
            }
        }
    }

    public Set<UUID> getUsersInRoom(UUID auctionId) {
        return auctionRoomUsers.getOrDefault(auctionId, Collections.emptySet());
    }


    public Set<UUID> addUserAndGetRoomUsers(UUID auctionId, UUID userId) {
        // Use thread-safe set and putIfAbsent logic
        auctionRoomUsers.computeIfAbsent(auctionId, k -> ConcurrentHashMap.newKeySet()).add(userId);

        // Return a new copy to avoid external modification
        return new HashSet<>(auctionRoomUsers.get(auctionId));
    }
}
