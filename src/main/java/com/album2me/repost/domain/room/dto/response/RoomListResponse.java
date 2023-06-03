package com.album2me.repost.domain.room.dto.response;

import java.util.List;

public record RoomListResponse(
        List<RoomResponse> rooms
) {
}
