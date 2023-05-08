package com.album2me.repost.domain.post.controller;

import com.album2me.repost.domain.auth.controller.VerifiedUser;
import com.album2me.repost.domain.post.dto.request.PostCreateRequest;
import com.album2me.repost.domain.post.dto.request.PostUpdateRequest;
import com.album2me.repost.domain.post.dto.response.PostResponse;
import com.album2me.repost.domain.post.service.PostService;

import com.album2me.repost.domain.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/albums/{albumId}/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> show(@PathVariable final Long id) {

        return ResponseEntity.ok(
                postService.findById(id)
        );
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @PathVariable final Long albumId,
            @VerifiedUser final User user,
            @Valid @RequestBody final PostCreateRequest postCreateRequest
    ) {
        final Long postId = postService.create(albumId, user.getId(), postCreateRequest);

        return ResponseEntity.created(
                URI.create("/api/albums/{albumId}/posts" + postId)
        ).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable final Long id,
            @VerifiedUser final User user,
            @Valid @RequestBody final PostUpdateRequest postUpdateRequest
    ) {
        postService.update(id, user, postUpdateRequest);

        return ResponseEntity.ok()
                .build();
    }
}
