package com.album2me.repost.domain.post.controller;

import com.album2me.repost.domain.auth.controller.VerifiedUser;
import com.album2me.repost.domain.post.dto.request.PostCreateRequest;
import com.album2me.repost.domain.post.dto.request.PostShowRequest;
import com.album2me.repost.domain.post.dto.request.PostUpdateRequest;
import com.album2me.repost.domain.post.dto.response.PostPageResponse;
import com.album2me.repost.domain.post.dto.response.PostResponse;
import com.album2me.repost.domain.post.service.PostService;

import com.album2me.repost.domain.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/rooms/{roomId}/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> showPageByPostId(@PathVariable final Long id) {

        return ResponseEntity.ok(
                postService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<PostPageResponse> showPage(
            @Valid @RequestBody final PostShowRequest postShowRequest,
            @PageableDefault(sort = "updated_at", direction = Sort.Direction.DESC) final Pageable pageable
    ) {
        return ResponseEntity.ok(
                postService.findAll(postShowRequest, pageable)
        );
    }


    @PostMapping
    public ResponseEntity<Void> create(
            @PathVariable final Long roomId,
            @VerifiedUser final User user,
            @Valid @RequestBody final PostCreateRequest postCreateRequest
    ) {
        final Long postId = postService.create(roomId, user, postCreateRequest);

        return ResponseEntity.created(
                URI.create("/api/rooms/{roomId}/posts" + postId)
        ).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable final Long id,
            @VerifiedUser final User user,
            @Valid @RequestBody final PostUpdateRequest postUpdateRequest
    ) {
        postService.update(id, user, postUpdateRequest);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Long id,
            @VerifiedUser final User user
    ) {
        postService.delete(id, user);

        return ResponseEntity.ok()
                .build();
    }
}
