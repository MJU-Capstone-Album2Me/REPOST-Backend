package com.album2me.repost.domain.album.dto.request;

import com.album2me.repost.domain.album.model.Album;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.user.model.User;

import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AlbumCreateRequest {

    @NotNull(message = "방이 없습니다.")
    private Long roomId;
    @NotNull(message = "이름이 없습니다.")
    private String name;

    @Builder
    public Album toEntity(final User user, final Room room) {
        return Album.builder()
                .user(user)
                .room(room)
                .name(name)
                .build();
    }
}
