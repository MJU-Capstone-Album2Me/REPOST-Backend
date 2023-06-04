package com.album2me.repost.domain.comment.controller;

import com.album2me.repost.domain.auth.controller.VerifiedUser;
import com.album2me.repost.domain.comment.dto.request.CommentCreateRequest;
import com.album2me.repost.domain.comment.dto.request.CommentUpdateRequest;
import com.album2me.repost.domain.comment.dto.response.CommentCreateResponse;
import com.album2me.repost.domain.comment.service.CommentService;
import com.album2me.repost.domain.user.model.User;

import com.album2me.repost.global.config.security.jwt.JwtAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/rooms/{roomId}/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentCreateResponse> create(
            @PathVariable final Long postId,
            @AuthenticationPrincipal JwtAuthentication jwtAuthentication,
            @Valid @RequestBody final CommentCreateRequest commentCreateRequest
    ) {
        final Long commentId = commentService.create(postId, jwtAuthentication.getId(), commentCreateRequest);

        return ResponseEntity.ok(
            new CommentCreateResponse(commentId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable final Long id,
            @AuthenticationPrincipal JwtAuthentication jwtAuthentication,
            @Valid @RequestBody final CommentUpdateRequest commentUpdateRequest
    ) {
        commentService.update(id, jwtAuthentication.getId(), commentUpdateRequest);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Long id,
            @AuthenticationPrincipal JwtAuthentication jwtAuthentication
    ) {
        commentService.delete(id, jwtAuthentication.getId());

        return ResponseEntity.ok()
                .build();
    }
}
