package com.album2me.repost.domain.album.dto.request;

import com.album2me.repost.domain.album.model.Album;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.user.model.User;

import jakarta.validation.constraints.NotNull;

public record AlbumCreateRequest (
    @NotNull(message = "방이 없습니다.") Long roomId,
    @NotNull(message = "이름이 없습니다.") String name
) {

    public Album toEntity(final User user, final Room room) {
        return Album.builder()
                .user(user)
                .room(room)
                .name(name)
                .build();
    }
}
