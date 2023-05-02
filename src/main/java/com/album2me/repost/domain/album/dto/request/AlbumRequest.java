package com.album2me.repost.domain.album.dto.request;

import com.album2me.repost.domain.album.model.Album;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AlbumRequest {

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
