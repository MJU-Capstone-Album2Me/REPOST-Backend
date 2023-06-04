package com.album2me.repost.domain.notification.controller;

import com.album2me.repost.domain.notification.dto.NotificationListResponse;
import com.album2me.repost.domain.notification.service.NotificationService;
import com.album2me.repost.global.config.security.jwt.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<NotificationListResponse> getNotifications(@AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
        return ResponseEntity.ok(notificationService.getNotifications(jwtAuthentication.getId()));
    }

}
