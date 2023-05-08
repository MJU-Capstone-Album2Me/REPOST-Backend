package com.album2me.repost.domain.post.dto.response;

import com.album2me.repost.domain.post.model.Post;

public record PostResponse (
    Long id,
    String title,
    String contents
) {

    public static PostResponse from(final Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContents());
    }
}
