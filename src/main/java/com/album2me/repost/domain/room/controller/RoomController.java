package com.album2me.repost.domain.room.controller;

import com.album2me.repost.domain.room.dto.request.RoomApplyApproveRequest;
import com.album2me.repost.domain.room.dto.request.RoomCreateRequest;
import com.album2me.repost.domain.room.dto.response.*;
import com.album2me.repost.domain.room.service.RoomService;
import com.album2me.repost.global.config.security.jwt.JwtAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/rooms")
public class RoomController {
    private final static String APPLY_REQUEST_KEY = "inviteCode";
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomCreateResponse> createRoom(@RequestBody @Valid RoomCreateRequest roomCreateRequest,
                                                         @AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
        return ResponseEntity.ok(
                roomService.createRoom(roomCreateRequest, jwtAuthentication.getId())
        );
    }

    @GetMapping
    public ResponseEntity<RoomListResponse> getRooms(@AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
        return ResponseEntity.ok(
                roomService.getRooms(jwtAuthentication.getId())
        );
    }

    @GetMapping("{roomId}/inviteCode")
    public ResponseEntity<RoomInviteCodeResponse> getInviteCode(@PathVariable Long roomId) {
        return ResponseEntity.ok(
                roomService.getInviteCode(roomId)
        );
    }

    @PostMapping("/apply")
    public ResponseEntity<Void> applyRoom(@RequestBody Map<String, String> applyRequest, @AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
        String inviteCode = applyRequest.get(APPLY_REQUEST_KEY);
        roomService.applyRoom(inviteCode, jwtAuthentication.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request/{roomApplyId}")
    public void approveApply(@RequestBody RoomApplyApproveRequest roomApplyApproveRequest, @PathVariable Long roomApplyId,
                             @AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
        roomService.approveApply(roomApplyApproveRequest, roomApplyId, jwtAuthentication.getId());
    }

    @GetMapping("{roomId}/members")
    public ResponseEntity<RoomMemberListResponse> getMembersInRoom(@PathVariable Long roomId, @AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
        return ResponseEntity.ok(roomService.getMembers(roomId, jwtAuthentication.getId()));
    }
}
