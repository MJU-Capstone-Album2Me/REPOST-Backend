package com.album2me.repost.domain.notification.model;

import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.global.common.BaseTimeColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Notification extends BaseTimeColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    private String message;
    private String link;
    private boolean isChecked;
}
