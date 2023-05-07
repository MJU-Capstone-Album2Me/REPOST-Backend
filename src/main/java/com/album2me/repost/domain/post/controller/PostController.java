package com.album2me.repost.domain.post.controller;

import com.album2me.repost.domain.auth.controller.VerifiedUser;
import com.album2me.repost.domain.post.dto.request.PostCreateRequest;
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
            @VerifiedUser User user,
            @Valid @RequestBody PostCreateRequest postCreateRequest
    ) {
        final Long postId = postService.create(user.getId(), postCreateRequest);

        return ResponseEntity.created(
                URI.create("/api/albums/{albumId}/posts" + postId)
        ).build();
    }


}
