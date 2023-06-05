package com.album2me.repost.domain.image.dto.response;

import com.album2me.repost.domain.image.model.Image;
import jakarta.validation.constraints.NotNull;

public record ImageResponse(
    @NotNull(message = "post의 id가 없습니다.")
    Long postId,

    @NotNull(message = "url이 없습니다.")
    String postImageUrl
) {

    public static ImageResponse from(final Image image) {
        return new ImageResponse (
            image.getPost().getId(),
            image.getPostImageUrl()
        );
    }
}
