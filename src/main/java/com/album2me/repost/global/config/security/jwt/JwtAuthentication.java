package com.album2me.repost.global.config.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtAuthentication {
    private final Long id;
    private final String authId;

}
