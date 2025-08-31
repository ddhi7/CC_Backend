package com.cc.authservice.service;

import com.cc.authservice.client.MemberClient;
import com.cc.authservice.dto.response.TokenResponse;
import com.cc.authservice.dto.response.MemberResponse;
import com.cc.authservice.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberClient memberClient;
    private final TokenProvider tokenProvider;

    public TokenResponse login(String email) {
            // 사용자 조회 (Feign Client 사용)
            MemberResponse member = memberClient.getByEmail(email);

            if (member == null) {
                throw new RuntimeException("User not found");
            }

            if (!"ACTIVE".equals(member.getActiveStatus())) {
                throw new RuntimeException("Inactive user");
            }

            return TokenResponse.builder()
                    .accessToken(tokenProvider.createAccessToken(member.getId().toString()))
                    .refreshToken(tokenProvider.createRefreshToken(member.getId().toString()))
                    .build();
    }

}