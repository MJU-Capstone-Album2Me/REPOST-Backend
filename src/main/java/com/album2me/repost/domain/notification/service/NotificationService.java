package com.album2me.repost.domain.notification.service;

import com.album2me.repost.domain.notification.model.Notification;
import com.album2me.repost.domain.notification.model.NotificationType;
import com.album2me.repost.domain.notification.repository.NotificationRepository;
import com.album2me.repost.domain.room.model.RoomApply;
import com.album2me.repost.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Transactional
    public void createApplyNotification(User user, User requester, RoomApply roomApply) {
        String message = requester.getNickName() + "님께서 가입 요청을 보냈습니다.";
        String link = null;
        Notification notification = Notification.builder()
                .notificationType(NotificationType.APPLY)
                .message(message)
                .user(user)
                .link(link)
                .build();
        notificationRepository.save(notification);
    }
}
