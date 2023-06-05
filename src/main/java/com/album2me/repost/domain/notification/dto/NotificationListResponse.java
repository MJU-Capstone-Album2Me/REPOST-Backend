package com.album2me.repost.domain.notification.dto;

import java.util.List;

public record NotificationListResponse(
        List<NotificationResponse> notifications
) {
}
