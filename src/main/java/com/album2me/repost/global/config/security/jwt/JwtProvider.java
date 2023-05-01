package com.album2me.repost.global.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import java.util.Collection;
import java.util.Date;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private final Algorithm algorithm;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.algorithm = Algorithm.HMAC256(jwtProperties.getClientSecret());
    }

    public String createAccessToken(String authId, String role){
        String accessToken = JWT.create()
                .withIssuer(jwtProperties.getIssuer())
                .withClaim("userKey", authId)
                .withClaim("role", role)
                .sign(algorithm);
        return accessToken;
    }

}
