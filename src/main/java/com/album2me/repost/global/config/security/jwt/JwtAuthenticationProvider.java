package com.album2me.repost.global.config.security.jwt;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

import com.album2me.repost.domain.auth.dto.response.AuthenticationResponse;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        return processUserAuthentication(String.valueOf(jwtAuthenticationToken.getPrincipal()),jwtAuthenticationToken.getCredentials());
    }

    private Authentication processUserAuthentication(String principal, String credential) {
        User user = userService.findUserByAuthId(principal);
        checkValidPassword(user, credential);

        String accessToken = jwtProvider.createAccessToken(user);
        String uuid = UUID.randomUUID().toString();

        String refreshToken = jwtProvider.createRefreshToken(uuid);
/**
        redisTemplate.opsForValue().set(uuid, refreshToken);

 **/
        JwtAuthenticationToken authenticated = new JwtAuthenticationToken(new JwtAuthentication(user.getId(), user.getAuthId()), null, createAuthorityList(user.getRole().name()));
        authenticated.setDetails(new AuthenticationResponse(accessToken, refreshToken));
        return authenticated;
    }

    private void checkValidPassword(User user, String credential){
        if(!passwordEncoder.matches(credential, user.getPassword())){
            throw new IllegalArgumentException("Bad Credential");
        }
    }

}
