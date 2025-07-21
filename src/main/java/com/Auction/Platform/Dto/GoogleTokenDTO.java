package com.Auction.Platform.Dto;

import lombok.Data;

@Data
public class GoogleTokenDTO {
    private String idToken;
    private String email;
    private String password;
    private String name;
}