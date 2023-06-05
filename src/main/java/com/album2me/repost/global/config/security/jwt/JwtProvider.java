package com.album2me.repost.global.config.security.jwt;

import com.album2me.repost.domain.user.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import java.util.Date;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private final Algorithm algorithm;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.algorithm = Algorithm.HMAC256(jwtProperties.getClientSecret());
    }

    public String createAccessToken(User user) {
        Date now = new Date();
        return JWT.create()
                .withIssuer(jwtProperties.getIssuer())
                .withClaim("id", user.getId())
                .withClaim("authId", user.getAuthId())
                .withArrayClaim("roles", new String[]{user.getRole().name()})
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + jwtProperties.getExpirySeconds() * 1000L))
                .sign(algorithm);
    }

    public String createRefreshToken(String payload) {
        Date now = new Date();
        return JWT.create()
                .withSubject(payload)
                .withIssuer(jwtProperties.getIssuer())
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + jwtProperties.getExpirySeconds() * 1000L))
                .sign(algorithm);
    }

    public Claims verifyToken(String token) {
        JWTVerifier jwtverifier = JWT.require(algorithm).withIssuer(jwtProperties.getIssuer()).build();
        return new Claims(jwtverifier.verify(token));
    }

    static public class Claims {
        Long id;
        String authId;
        String[] roles;
        Date iat;
        Date exp;


        Claims(DecodedJWT decodedJWT) {
            Claim id = decodedJWT.getClaim("id");
            if(!id.isNull()){
                this.id = id.asLong();
            }
            Claim authId = decodedJWT.getClaim("authId");
            if(!authId.isNull()){
                this.authId = authId.asString();
            }
            Claim roles = decodedJWT.getClaim("roles");
            if(!roles.isNull()){
                this.roles = roles.asArray(String.class);
            }
            this.iat = decodedJWT.getIssuedAt();
            this.exp = decodedJWT.getExpiresAt();
        }
    }

}
