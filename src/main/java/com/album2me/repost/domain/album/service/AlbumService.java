package com.album2me.repost.domain.album.service;

import com.album2me.repost.domain.album.dto.response.AlbumResponse;
import com.album2me.repost.domain.album.model.Album;
import com.album2me.repost.domain.album.repository.AlbumRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    public List<AlbumResponse> showAll() {
        final List<Album> albums = albumRepository.findAll();

        return albums.stream()
                .map(AlbumResponse::from)
                .collect(Collectors.toList());
    }
}
