package com.album2me.repost.domain.room.dto.request;

import jakarta.validation.constraints.NotNull;

public record RoomApplyApproveRequest(
        @NotNull(message = "null이 될 수 없습니다.")
        Long notificationId
) {
}
