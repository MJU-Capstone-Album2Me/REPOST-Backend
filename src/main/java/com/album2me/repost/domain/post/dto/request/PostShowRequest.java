package com.album2me.repost.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;

public record PostShowRequest(
        @NotNull(message = "cursor가 없습니다.")
        Long cursor
) {

}


