package com.album2me.repost.domain.album.dto.request;

import com.album2me.repost.domain.album.model.Album;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AlbumRequest {

    private Long roomId;
    private Long writerId;
    private String thumbnailUrl;
    private String name;
    private Long postCount;

    @Builder
    public Album toEntity() {
        return Album.builder()
                .roomId(roomId)
                .writerId(writerId)
                .thumbnailUrl(thumbnailUrl)
                .name(name)
                .postCount(postCount)
                .build();
    }
}
