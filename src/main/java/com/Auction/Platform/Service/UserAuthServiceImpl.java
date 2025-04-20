package com.Auction.Platform.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Auction.Platform.Dto.GoogleTokenDTO;
import com.Auction.Platform.Entity.User;
import com.Auction.Platform.Repository.UserRepository;
@Service
public class UserAuthServiceImpl implements UserAuthService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public ResponseEntity<?> authenticate(GoogleTokenDTO tokenDTO) {
		// TODO Auto-generated method stub
		   String token = tokenDTO.getIdToken();

	        RestTemplate restTemplate = new RestTemplate();
	        String googleUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + token;

	        try {
	            Map<String, String> response = restTemplate.getForObject(googleUrl, Map.class);

	            if (response != null && response.get("email") != null) {
	                String email = response.get("email");
	                String name = response.get("name");

	                User user = userRepository.findByEmail(email);
	                if (user == null) {
	                    user = new User();
	                    user.setEmail(email);
	                    user.setName(name);
	                    user.setPasswordHash(""); // Set if needed
	                    user.setRole("BUYER");
	                    user.setCreatedAt(LocalDateTime.now());
	                    userRepository.save(user);
	                }
	                
	                Map<String, String> result = new HashMap<>();
	                result.put("message", "Welcome " + name);
	                return ResponseEntity.ok(result);
	            } else {
	                return ResponseEntity.status(401).body("Invalid ID Token");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Google token verification failed");
	        }	
	}

}
