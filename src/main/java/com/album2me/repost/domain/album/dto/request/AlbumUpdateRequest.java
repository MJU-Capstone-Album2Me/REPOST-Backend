package com.album2me.repost.domain.album.dto.request;

import com.album2me.repost.domain.album.model.Album;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AlbumUpdateRequest {

    @NotNull(message = "앨범명이 입력되지 않았습니다.")
    private String name;

    private String thumbnailUrl;

    @Builder
    public Album toEntity() {
        return Album.builder()
                .name(name)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
