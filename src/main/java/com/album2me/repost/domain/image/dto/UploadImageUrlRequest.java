package com.album2me.repost.domain.image.dto;

import com.album2me.repost.domain.image.model.Image;
import com.album2me.repost.domain.post.model.Post;

import jakarta.validation.constraints.NotNull;

public record UploadImageUrlRequest (
        Post post,
        @NotNull(message = "이미지 url이 없습니다.") String url
) {
    public static UploadImageUrlRequest of(final Post post, final String postImageUrl) {
        return new UploadImageUrlRequest(post, postImageUrl);
    }

    public Image toEntity(final Post post, final String url) {
        return Image.builder()
                .post(post)
                .postImageUrl(url)
                .build();
    }
}