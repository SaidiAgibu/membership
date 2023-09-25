package com.codeVirtus.demo.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String tokenType = "Bearer ";
    private String accessToken;



    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;

    }

}