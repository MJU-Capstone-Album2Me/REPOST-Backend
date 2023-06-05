package com.album2me.repost.domain.notification.repository;

import com.album2me.repost.domain.notification.model.Notification;
import com.album2me.repost.domain.user.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserOrderByCreatedAtDesc(User user);
}
