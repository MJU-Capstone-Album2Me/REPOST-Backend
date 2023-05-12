package com.album2me.repost.domain.comment.controller;

import com.album2me.repost.domain.auth.controller.VerifiedUser;
import com.album2me.repost.domain.comment.dto.request.CommentCreateRequest;
import com.album2me.repost.domain.comment.dto.request.CommentUpdateRequest;
import com.album2me.repost.domain.comment.service.CommentService;
import com.album2me.repost.domain.user.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/albums/{albumId}/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> create(
            @PathVariable final Long postId,
            @VerifiedUser final User user,
            @Valid @RequestBody final CommentCreateRequest commentCreateRequest
    ) {
        final Long commentId =  commentService.create(postId, user.getId(), commentCreateRequest);

        return ResponseEntity.created(
                    URI.create("/api/albums/{albumId}/posts/{postId}/comments" + commentId)
                )
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable final Long id,
            @VerifiedUser final User user,
            @Valid @RequestBody final CommentUpdateRequest commentUpdateRequest
    ) {
        commentService.update(id, user.getId(), commentUpdateRequest);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Long id,
            @VerifiedUser final User user
    ) {
        commentService.delete(id, user.getId());

        return ResponseEntity.ok()
                .build();
    }
}
