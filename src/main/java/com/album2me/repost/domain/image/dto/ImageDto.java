package com.album2me.repost.domain.image.dto;

import com.album2me.repost.domain.image.model.Image;
import jakarta.validation.constraints.NotNull;

public record ImageDto(
        @NotNull(message = "id가 없습니다.")
        Long id,

        @NotNull(message = "url이 없습니다.")
        String postImageUrl

) {
    public static ImageDto from(final Image image) {
        return new ImageDto(
                image.getId(),
                image.getPostImageUrl()
        );
    }
}
