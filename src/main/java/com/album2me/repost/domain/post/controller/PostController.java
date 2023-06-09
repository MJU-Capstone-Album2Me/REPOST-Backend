package com.album2me.repost.domain.post.controller;

import com.album2me.repost.domain.post.dto.request.PostCreateRequest;
import com.album2me.repost.domain.post.dto.request.PostUpdateRequest;
import com.album2me.repost.domain.post.dto.response.GalleryPageResponse;
import com.album2me.repost.domain.post.dto.response.PostCreateResponse;
import com.album2me.repost.domain.post.dto.response.PostPageResponse;
import com.album2me.repost.domain.post.dto.response.PostWithCommentsResponse;
import com.album2me.repost.domain.post.service.PostService;

import com.album2me.repost.global.config.security.jwt.JwtAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms/{roomId}/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostWithCommentsResponse> showPageByPostId(@PathVariable final Long id) {

        return ResponseEntity.ok(
                postService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<PostPageResponse> showPage(
            @PathVariable final Long roomId,
            @RequestParam("cursor") final Long cursor,
            @PageableDefault(sort = "updated_at", direction = Sort.Direction.DESC, size = 3) final Pageable pageable
    ) {
        return ResponseEntity.ok(
                postService.findAll(roomId, cursor, pageable)
        );
    }

    @GetMapping("/images")
    public ResponseEntity<GalleryPageResponse> showGallery (
            @PathVariable final Long roomId,
            @RequestParam("cursor") final Long cursor,
            @PageableDefault(sort = "updated_at", direction = Sort.Direction.DESC, size = 18) final Pageable pageable
            )
    {
        return ResponseEntity.ok(
                postService.findFirstImageForPosts(roomId, cursor, pageable)
        );
    }

    @PostMapping
    public ResponseEntity<PostCreateResponse> create(
            @PathVariable final Long roomId,
            @AuthenticationPrincipal JwtAuthentication jwtAuthentication,
            @Valid @RequestBody final PostCreateRequest postCreateRequest
    ) {
        return ResponseEntity.ok(postService.create(roomId, jwtAuthentication.getId(), postCreateRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable final Long id,
            @AuthenticationPrincipal JwtAuthentication jwtAuthentication,
            @Valid @ModelAttribute final PostUpdateRequest postUpdateRequest
    ) {
        postService.update(id, jwtAuthentication.getId(), postUpdateRequest);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Long id,
            @AuthenticationPrincipal JwtAuthentication jwtAuthentication
    ) {
        postService.delete(id, jwtAuthentication.getId());

        return ResponseEntity.ok()
                .build();
    }
}
