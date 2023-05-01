package com.album2me.repost.global.config.security.jwt;

import com.album2me.repost.domain.user.model.User;

public class JwtAuthentication {
    private final User user;

    public JwtAuthentication(User user) {
        this.user = user;
    }
}
