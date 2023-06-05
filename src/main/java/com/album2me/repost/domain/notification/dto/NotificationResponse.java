package com.album2me.repost.domain.notification.dto;

import com.album2me.repost.domain.notification.model.Notification;

public record NotificationResponse(
        Long id,
        String type,
        String message,
        String pastTime,
        Long resourceId,
        Boolean approved
) {
    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(notification.getId(), notification.getNotificationType().name(),
                notification.getMessage(), notification.getPastTime(), notification.getResourceId(), notification.getApproved());
    }
}
