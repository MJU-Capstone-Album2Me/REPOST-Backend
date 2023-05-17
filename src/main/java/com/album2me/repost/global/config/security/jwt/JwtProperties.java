package com.album2me.repost.global.config.security.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperties {
    private final String issuer;
    private final String clientSecret;
    private final int expirySeconds;

    public JwtProperties(
            @Value("${jwt.token.issuer}") String issuer,
            @Value("${jwt.token.client-secret}")String clientSecret,
            @Value("${jwt.token.expirySeconds}") int expirySeconds) {
        this.issuer = issuer;
        this.clientSecret = clientSecret;
        this.expirySeconds = expirySeconds;
    }
}
