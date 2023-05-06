package com.album2me.repost.global.config.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null){
            String authorizationToken = obtainAuthorization(request);
            if(authorizationToken!=null) {
                try {
                    JwtProvider.Claims claims = jwtProvider.verifyToken(authorizationToken);
                    Long id = claims.id;
                    String authId = claims.authId;
                    List<GrantedAuthority> authorities = obtainAuthorities(claims);
                    JwtAuthenticationToken authentication =
                            new JwtAuthenticationToken(new JwtAuthentication(id, authId), null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception e) {
                    log.warn("Jwt processing failed: {}", e.getMessage());
                }
            }
        } else {
            log.debug("SecurityContextHolder not populated with security token, as it already contained: '{}'",
                    SecurityContextHolder.getContext().getAuthentication());
        }
        filterChain.doFilter(request, response);
    }

    private String obtainAuthorization(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token != null) {
            return token.replace("Bearer ", "");
        }
        return null;
    }

    private List<GrantedAuthority> obtainAuthorities(JwtProvider.Claims claims) {
        String[] roles = claims.roles;
        return roles == null || roles.length == 0 ? Collections.emptyList() :
                Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
