package com.album2me.repost.domain.post.dto.response;

import com.album2me.repost.domain.image.dto.ImageDto;
import com.album2me.repost.domain.post.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public record PostResponse(
        Long id,
        String title,
        String contents,
        List<ImageDto> images
) {

    public static PostResponse from(Post post) {
        return new PostResponse (
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getImages().stream().map(ImageDto::from).collect(Collectors.toList())
        );
    }
}
