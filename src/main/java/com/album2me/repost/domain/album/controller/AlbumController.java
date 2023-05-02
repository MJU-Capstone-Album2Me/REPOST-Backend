package com.album2me.repost.domain.album.controller;

import com.album2me.repost.domain.album.dto.request.AlbumRequest;
import com.album2me.repost.domain.album.dto.response.AlbumResponse;
import com.album2me.repost.domain.album.service.AlbumService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public List<AlbumResponse> showAll() {
        return albumService.showAll();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody final AlbumRequest albumRequest) {
        final Long albumId = albumService.create(albumRequest).getId();

        return ResponseEntity.created(URI.create("api/albums" + albumId))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable final Long id,
            @RequestBody final AlbumRequest albumRequest
    ) {
        albumService.update(id, albumRequest);

        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        albumService.delete(id);

        return ResponseEntity.noContent()
                .build();
    }
}
