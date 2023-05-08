package com.album2me.repost.domain.album.dto.response;

import com.album2me.repost.domain.album.model.Album;

public record AlbumResponse (
        Long id,
        String thumbnailUrl,
        String name
) {

    public static AlbumResponse from(final Album album) {
        return new AlbumResponse(album.getId(), album.getThumbnailUrl(), album.getName());
    }
}
