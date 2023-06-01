package com.album2me.repost.domain.post.dto.response;

import com.album2me.repost.domain.image.model.Image;
import com.album2me.repost.domain.post.model.Post;

import java.util.List;

public record PostResponse (
        Long id,
        String title,
        String contents,
        List<Image> images
) {

    public static PostResponse from(final Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContents(), post.getImages());
    }
}
