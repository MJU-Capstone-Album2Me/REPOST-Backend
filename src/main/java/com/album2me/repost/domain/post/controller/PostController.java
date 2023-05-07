package com.album2me.repost.domain.post.controller;

import com.album2me.repost.domain.post.dto.response.PostResponse;
import com.album2me.repost.domain.post.service.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> show(@PathVariable final Long id) {

        return ResponseEntity.ok(
                postService.findById(id)
        );
    }
}
