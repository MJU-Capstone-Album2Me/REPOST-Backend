package com.album2me.repost.domain.auth.dto.request;


public record AuthenticationRequest(
        String authId,
        String password
) {

}
