package com.Auction.Platform.Service;

import org.springframework.http.ResponseEntity;

import com.Auction.Platform.Dto.GoogleTokenDTO;


public interface UserAuthService {
	
	ResponseEntity<?> authenticate(GoogleTokenDTO tokenDTO);

}
