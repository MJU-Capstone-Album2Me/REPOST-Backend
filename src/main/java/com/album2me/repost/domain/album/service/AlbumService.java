package com.album2me.repost.domain.album.service;

import com.album2me.repost.domain.album.dto.request.AlbumRequest;
import com.album2me.repost.domain.album.dto.response.AlbumResponse;
import com.album2me.repost.domain.album.model.Album;
import com.album2me.repost.domain.album.repository.AlbumRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public AlbumResponse create(final AlbumRequest albumRequest) {
        final Album newAlbum = albumRepository.save(albumRequest.toEntity());

        return AlbumResponse.from(newAlbum);
    }

    @Transactional
    public AlbumResponse update(final Long id, final AlbumRequest albumRequest) {
        final Album album = albumRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        album.update(albumRequest.toEntity());

        return AlbumResponse.from(album);
    }
}
