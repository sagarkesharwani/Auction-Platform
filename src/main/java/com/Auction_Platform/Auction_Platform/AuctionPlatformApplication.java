package com.Auction_Platform.Auction_Platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.Auction.Platform")
@EnableJpaRepositories(basePackages = "com.Auction.Platform.Repository")
@EntityScan(basePackages = "com.Auction.Platform.Entity")
public class AuctionPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionPlatformApplication.class, args);
	}

}
