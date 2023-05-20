package com.album2me.repost.domain.room.dto.response;

import java.util.List;

public record RoomApplyListResponse(
        List<RoomApplyResponse> applications
) {
}
