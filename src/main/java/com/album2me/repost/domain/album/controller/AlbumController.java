package com.album2me.repost.domain.album.controller;

import com.album2me.repost.domain.album.dto.response.AlbumResponse;
import com.album2me.repost.domain.album.service.AlbumService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
