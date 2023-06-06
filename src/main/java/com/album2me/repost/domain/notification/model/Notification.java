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
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Enumerated(EnumType.STRING)
    private NotificationCommand notificationType;
    private String message;
    private Long resourceId;
    private Boolean approved;

    @Builder
    public Notification(User user, NotificationCommand notificationType, String message, Long resourceId, Boolean approved) {
        this.user = user;
        this.notificationType = notificationType;
        this.message = message;
        this.resourceId = resourceId;
        this.approved = approved;
    }

    public void changeApproved(){
        approved = true;
    }

    public String getPastTime() {
        Duration duration = Duration.between(getCreatedAt(), LocalDateTime.now());
        long seconds = duration.getSeconds();
        long weeks = seconds / (60 * 60 * 24 * 7);
        long days = seconds / (60 * 60 * 24) % 7;
        long hours = seconds / (60 * 60) % 24;
        long minutes = seconds / 60 % 60;
        long remainingSeconds = seconds % 60;
        if (seconds < 60) {
            return remainingSeconds + "초";
        } else if (seconds < 3600) {
            return minutes + "분";
        } else if (seconds < 3600 * 24) {
            return hours + "시간";
        } else if (seconds < 3600 * 24 * 7) {
            return days + "일";
        } else {
            return weeks + "주";
        }
    }
}
