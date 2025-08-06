package com.cc.authservice.dto.response;


import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class TokenResponse {
    private final String accessToken;
    private final String refreshToken;
}

