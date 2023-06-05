package com.album2me.repost.domain.auth.dto.response;

public record AuthenticationResponse(
        String accessToken,
        String refreshToken
) {

}
