package com.album2me.repost.domain.album.controller;

import com.album2me.repost.domain.album.dto.request.AlbumCreateRequest;
import com.album2me.repost.domain.album.dto.request.AlbumUpdateRequest;
import com.album2me.repost.domain.album.dto.response.AlbumResponse;
import com.album2me.repost.domain.album.service.AlbumService;
import com.album2me.repost.domain.auth.controller.VerifiedUser;
import com.album2me.repost.domain.user.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public List<AlbumResponse> showAll() {
        return albumService.showAll();
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @VerifiedUser User user,
            @Valid @RequestBody final AlbumCreateRequest albumCreateRequest
    ) {
        final Long albumId = albumService.create(user.getId(), albumCreateRequest);

        return ResponseEntity.created(URI.create("api/albums" + albumId))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable final Long id,
            @VerifiedUser User user,
            @Valid @RequestBody final AlbumUpdateRequest albumUpdateRequest
    ) {
        albumService.update(id, albumUpdateRequest);

        return ResponseEntity.ok()
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Long id,
            @VerifiedUser User user
    ) {

        albumService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
}
