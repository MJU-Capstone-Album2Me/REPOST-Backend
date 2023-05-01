package com.album2me.repost.domain.album.dto.response;

import com.album2me.repost.domain.album.model.Album;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.beans.BeanUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumResponse {

    private Long id;
    private Long roomId;
    private Long writerId;
    private String thumbnailUrl;
    private String name;
    private Long postCount;

    public static AlbumResponse from(final Album album) {
        final AlbumResponse albumResponse = new AlbumResponse();
        BeanUtils.copyProperties(album, albumResponse);

        return albumResponse;
    }
}
