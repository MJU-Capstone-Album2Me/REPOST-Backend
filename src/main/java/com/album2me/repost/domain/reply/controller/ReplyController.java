package com.album2me.repost.domain.reply.controller;

import com.album2me.repost.domain.auth.controller.VerifiedUser;
import com.album2me.repost.domain.reply.dto.request.ReplyCreateRequest;
import com.album2me.repost.domain.reply.service.ReplyService;
import com.album2me.repost.domain.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/albums/{albumId}/posts/{postId}/comments/{commentId}/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<Void> create(
            @PathVariable final Long commentId,
            @VerifiedUser User user,
            @Valid @RequestBody ReplyCreateRequest replyCreateRequest
    ) {
        final Long replyId = replyService.create(commentId, user.getId(), replyCreateRequest);

        return ResponseEntity.created(
                URI.create("api/albums/{albumId}/posts/{postId}/comments/{commentId}/replies" + replyId)
        ).build();
    }
}
