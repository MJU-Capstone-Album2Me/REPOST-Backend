package com.album2me.repost.domain.notification.repository;

import com.album2me.repost.domain.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
