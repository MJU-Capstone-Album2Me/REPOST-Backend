package com.album2me.repost.domain.post.dto.response;

import com.album2me.repost.domain.post.model.Post;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public record GalleryPageResponse (
        List<GalleryResponse> items
) {
    public static GalleryPageResponse from(final Slice<Post> page) {
        return new GalleryPageResponse ((page.stream()
                .map(GalleryResponse::from)
                .collect(Collectors.toList()))
        );
    }
}
