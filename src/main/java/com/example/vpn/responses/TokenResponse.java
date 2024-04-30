package com.example.vpn.responses;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class TokenResponse {

    public static ResponseEntity<Object> tokenResponseBuilder(
            String message,
            String accessToken,
            String refreshToken
    ) {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", message);
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);

        return  ResponseEntity.ok(map);
    }
}
