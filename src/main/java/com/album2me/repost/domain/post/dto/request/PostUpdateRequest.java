package com.album2me.repost.domain.post.dto.request;

import com.album2me.repost.domain.post.model.Post;

import jakarta.validation.constraints.NotNull;

public record PostUpdateRequest (
    @NotNull(message = "제목이 없습니다.") String title,

    @NotNull(message = "내용이 없습니다.") String contents
) {

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .contents(contents)
                .build();
    }
}