package com.Auction.Platform.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auction.Platform.Dto.GoogleTokenDTO;
import com.Auction.Platform.Service.UserAuthService;

@RestController
@RequestMapping("api/auth")
public class LoginController {

	@Autowired
	UserAuthService userAuthService;
    
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody GoogleTokenDTO tokenDTO) {
    	logger.info("User logged in: {}", tokenDTO);
    	return userAuthService.authenticate(tokenDTO);
    }
}