package com.album2me.repost.domain.post.dto.response;

import com.album2me.repost.domain.post.model.Post;
import jakarta.validation.constraints.NotNull;

public record GalleryResponse(
        @NotNull(message = "post의 id가 없습니다.")
        Long postId,

        @NotNull(message = "url이 없습니다.")
        String postImageUrl
) {

    public static GalleryResponse from(final Post post) {
        return new GalleryResponse (
                post.getId(),
                post.getImages().get(0).getPostImageUrl()
        );
    }
}

