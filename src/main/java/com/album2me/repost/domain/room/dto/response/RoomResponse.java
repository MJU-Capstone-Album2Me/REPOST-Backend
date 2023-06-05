package com.album2me.repost.domain.room.dto.response;

public record RoomResponse(
        Long id,
        String name,
        int membersCount,
        boolean isHost
) {
}
