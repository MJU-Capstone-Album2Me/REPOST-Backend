package com.album2me.repost.domain.post.dto.request;

import com.album2me.repost.domain.album.model.Album;
import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.user.model.User;

import jakarta.validation.constraints.NotNull;

public record PostCreateRequest (
    @NotNull(message = "제목이 없습니다.") String title,

    @NotNull(message = "내용이 없습니다.") String contents
) {

    public Post toEntity(final User user, final Album album) {
        return Post.builder()
                .user(user)
                .album(album)
                .title(title)
                .contents(contents)
                .build();
    }
}