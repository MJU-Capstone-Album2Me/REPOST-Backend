package com.album2me.repost.domain.room.controller;

import com.album2me.repost.domain.room.dto.RoomCreateRequest;
import com.album2me.repost.domain.room.dto.RoomCreateResponse;
import com.album2me.repost.domain.room.service.RoomService;
import com.album2me.repost.global.config.security.jwt.JwtAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/room")
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomCreateResponse> createRoom(@RequestBody @Valid RoomCreateRequest roomCreateRequest,
                                                         @AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
        return ResponseEntity.ok(
                roomService.createRoom(roomCreateRequest, jwtAuthentication.getId())
        );
    }
}
