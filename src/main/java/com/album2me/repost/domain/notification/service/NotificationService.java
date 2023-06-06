package com.album2me.repost.domain.notification.service;

import com.album2me.repost.domain.notification.dto.NotificationListResponse;
import com.album2me.repost.domain.notification.dto.NotificationResponse;
import com.album2me.repost.domain.notification.model.Notification;
import com.album2me.repost.domain.notification.model.NotificationCommand;
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
        String message = NotificationCommand.APPLY.createMessage(requester.getNickName());
        Notification notification = Notification.builder()
                .notificationType(NotificationCommand.APPLY)
                .message(message)
                .user(user)
                .resourceId(roomApply.getId())
                .approved(false)
                .build();
        notificationRepository.save(notification);
    }

    @Transactional
    public void createApplyApproveNotification(final User user, final Room room) {
        String message = NotificationCommand.ENTRANCE.createMessage(room.getName());
        Notification notification = Notification.builder()
                .notificationType(NotificationCommand.ENTRANCE)
                .message(message)
                .user(user)
                .build();
        notificationRepository.save(notification);
    }

    @Transactional
    public void createCommentNotification(final User user, final User postWriter, final Long postId) {
        String message = NotificationCommand.COMMENT.createMessage(user.getNickName());

        Notification notificationForPostWriter = Notification.builder()
                .notificationType(NotificationCommand.COMMENT)
                .message(message)
                .user(postWriter)
                .resourceId(postId)
                .build();
        notificationRepository.save(notificationForPostWriter);
    }

    @Transactional
    public void createReplyNotificationToPostWriter(final User user, final User postWriter, final Long postId) {
        String message = NotificationCommand.REPLY.createMessage(user.getNickName());

        Notification notificationForPostWriter = Notification.builder()
                .notificationType(NotificationCommand.REPLY)
                .message(message)
                .user(postWriter)
                .resourceId(postId)
                .build();
        notificationRepository.save(notificationForPostWriter);
    }

    @Transactional
    public void createReplyNotificationToCommentWriter(final User user, final User commentWriter, final Long postId) {
        String message = NotificationCommand.REPLY.createMessage(user.getNickName());

        Notification notificationForCommentWriter = Notification.builder()
                .notificationType(NotificationCommand.REPLY)
                .message(message)
                .user(commentWriter)
                .resourceId(postId)
                .build();
        notificationRepository.save(notificationForCommentWriter);
    }

    public Notification findNotificationById(Long id) {
        return notificationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 알림이 존재하지 않습니다."));
    }

}
