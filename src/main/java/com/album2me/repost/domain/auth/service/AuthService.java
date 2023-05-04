package com.album2me.repost.domain.auth.service;

import com.album2me.repost.domain.auth.dto.request.AuthenticationRequest;
import com.album2me.repost.domain.auth.dto.response.AuthenticationResponse;
import com.album2me.repost.global.config.security.jwt.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest){
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(authenticationRequest.authId(), authenticationRequest.password());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new AuthenticationResponse(String.valueOf(authentication.getDetails()));
    }

}
