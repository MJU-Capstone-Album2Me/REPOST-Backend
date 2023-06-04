package com.album2me.repost.domain.post.dto.response;

import jakarta.validation.constraints.NotNull;

public record PostCreateResponse(
    @NotNull(message = "id가 없습니다.")
    Long postId
) {
}
