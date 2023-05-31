package com.album2me.repost.domain.image.dto;

import com.album2me.repost.domain.image.model.Image;
import com.album2me.repost.domain.post.model.Post;

import jakarta.validation.constraints.NotNull;

public record UploadImageUrlRequest (
        @NotNull(message = "포스트 id가 없습니다.") Long postId,
        @NotNull(message = "이미지 url이 없습니다.") String url
) {
    public static UploadImageUrlRequest of(final Long postId, final String postImageUrl) {
        return new UploadImageUrlRequest(postId, postImageUrl);
    }

    public Image toEntity(final Post post, final String url) {
        return Image.builder()
                .post(post)
                .postImageUrl(url)
                .build();
    }
}