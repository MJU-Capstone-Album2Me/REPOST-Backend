package com.album2me.repost.global.config.security.jwt;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

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
        String token = jwtProvider.createAccessToken(user.getAuthId(), user.getRole());
        JwtAuthenticationToken authenticated = new JwtAuthenticationToken(new JwtAuthentication(user), null, createAuthorityList(user.getRole()));
        authenticated.setDetails(token);
        return authenticated;
    }

    private void checkValidPassword(User user, String credential){
        if(!passwordEncoder.matches(credential, user.getPassword())){
            throw new IllegalArgumentException("Bad Credential");
        }
    }

}
