package com.album2me.repost.domain.album.dto.request;

import com.album2me.repost.domain.album.model.Album;
import jakarta.validation.constraints.NotNull;

public record AlbumUpdateRequest (
    @NotNull(message = "앨범명이 입력되지 않았습니다.") String name,
    String thumbnailUrl

) {

    public Album toEntity() {
        return Album.builder()
                .name(name)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
