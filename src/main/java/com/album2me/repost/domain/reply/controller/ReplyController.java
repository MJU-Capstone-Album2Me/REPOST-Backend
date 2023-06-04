package com.album2me.repost.domain.reply.controller;

import com.album2me.repost.domain.auth.controller.VerifiedUser;
import com.album2me.repost.domain.reply.domain.Reply;
import com.album2me.repost.domain.reply.dto.request.ReplyCreateRequest;
import com.album2me.repost.domain.reply.dto.request.ReplyUpdateRequest;
import com.album2me.repost.domain.reply.dto.response.ReplyCreateResponse;
import com.album2me.repost.domain.reply.service.ReplyService;
import com.album2me.repost.domain.user.model.User;

import com.album2me.repost.global.config.security.jwt.JwtAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/rooms/{roomId}/posts/{postId}/comments/{commentId}/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<ReplyCreateResponse> create(
            @PathVariable final Long commentId,
            @AuthenticationPrincipal JwtAuthentication jwtAuthentication,
            @Valid @RequestBody ReplyCreateRequest replyCreateRequest
    ) {
        final Long replyId = replyService.create(commentId, jwtAuthentication.getId(), replyCreateRequest);

        return ResponseEntity.ok(
            new ReplyCreateResponse(replyId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable final Long id,
            @AuthenticationPrincipal JwtAuthentication jwtAuthentication,
            @Valid @RequestBody final ReplyUpdateRequest replyUpdateRequest
    ) {
        replyService.update(id, jwtAuthentication.getId(), replyUpdateRequest);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Long id,
            @AuthenticationPrincipal JwtAuthentication jwtAuthentication
    ) {
        replyService.delete(id, jwtAuthentication.getId());

        return ResponseEntity.ok()
                .build();
    }
}
