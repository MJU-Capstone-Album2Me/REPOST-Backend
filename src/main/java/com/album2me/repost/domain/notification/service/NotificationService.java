package com.album2me.repost.domain.notification.service;

import com.album2me.repost.domain.notification.dto.NotificationListResponse;
import com.album2me.repost.domain.notification.dto.NotificationResponse;
import com.album2me.repost.domain.notification.model.Notification;
import com.album2me.repost.domain.notification.model.NotificationType;
import com.album2me.repost.domain.notification.repository.NotificationRepository;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.room.model.RoomApply;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.service.UserService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public NotificationListResponse getNotifications(Long loginId) {
        User loginUser = userService.findUserById(loginId);
        return new NotificationListResponse(notificationRepository.findAllByUserOrderByCreatedAtDesc(loginUser).stream()
                .map(NotificationResponse::from).toList());
    }

    @Transactional
    public void createApplyNotification(User user, User requester, RoomApply roomApply) {
        String message = requester.getNickName() + "님께서 가입 요청을 보냈습니다.";
        String link = "rooms/request/" + roomApply.getId();
        Notification notification = Notification.builder()
                .notificationType(NotificationType.APPLY_CHECK)
                .message(message)
                .user(user)
                .link(link)
                .build();
        notificationRepository.save(notification);
    }

    @Transactional
    public void createApplyApproveNotification(User user, Room room) {
        String message = room.getName() + "에 가입이 완료되셨습니다.";
        Notification notification = Notification.builder()
                .notificationType(NotificationType.APPLY_APPROVE)
                .message(message)
                .user(user)
                .link(null)
                .build();
        notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(Long id){
        Notification notification = findNotificationById(id);
        notificationRepository.delete(notification);
    }

    public Notification findNotificationById(Long id) {
        return notificationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 알림이 존재하지 않습니다."));
    }

}
