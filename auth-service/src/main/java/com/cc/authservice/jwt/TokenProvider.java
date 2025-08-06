package com.cc.authservice.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;


@Component
@Slf4j
public class TokenProvider {

    private static final long ACCESS_TOKEN_VALIDITY_MS = 5 * 60 * 60 * 1000;
    private static final long REFRESH_TOKEN_VALIDITY_MS = 7 * 24 * 60 * 60 * 1000;

    private final SecretKey key;
    private final RedisTemplate<String, String> redisTemplate;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                            RedisTemplate<String, String> redisTemplate) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.redisTemplate = redisTemplate;
    }

    public String createAccessToken(String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_MS);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("type", "access")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String userId) {
        String refreshTokenId = UUID.randomUUID().toString();
        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_VALIDITY_MS);

        String refreshToken = Jwts.builder()
                .setSubject(userId)
                .setId(refreshTokenId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("type", "refresh")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Redis에 저장
        String redisKey = "refresh_token:" + userId;
        redisTemplate.opsForValue().set(redisKey, refreshTokenId, Duration.ofDays(7));

        log.debug("Refresh token stored in Redis for user: {}", userId);
        return refreshToken;
    }

    public boolean validateAccessToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return "access".equals(claims.get("type"));
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid access token: {}", e.getMessage());
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (!"refresh".equals(claims.get("type"))) {
                return false;
            }

            String userId = claims.getSubject();
            String tokenId = claims.getId();

            // Redis에서 저장된 Refresh Token ID 확인
            String redisKey = "refresh_token:" + userId;
            String storedTokenId = redisTemplate.opsForValue().get(redisKey);

            return tokenId.equals(storedTokenId);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid refresh token: {}", e.getMessage());
            return false;
        }
    }

    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public void deleteRefreshToken(String userId) {
        String redisKey = "refresh_token:" + userId;
        redisTemplate.delete(redisKey);
        log.debug("Refresh token deleted from Redis for user: {}", userId);
    }

    public boolean isRefreshTokenExists(String userId) {
        String redisKey = "refresh_token:" + userId;
        return redisTemplate.hasKey(redisKey);
    }
}