package com.album2me.repost.domain.image.dto.response;

import com.album2me.repost.domain.image.model.Image;

import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public record ImagePageResponse (
        List<ImageResponse> items
) {
    public static ImagePageResponse from(final Slice<Image> page) {
        return new ImagePageResponse((page.stream()
                .map(ImageResponse::from)
                .collect(Collectors.toList()))
        );
    }
}
