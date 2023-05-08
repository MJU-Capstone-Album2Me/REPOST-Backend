package com.album2me.repost.domain.album.service;

import com.album2me.repost.domain.album.dto.request.AlbumCreateRequest;
import com.album2me.repost.domain.album.dto.request.AlbumUpdateRequest;
import com.album2me.repost.domain.album.dto.response.AlbumResponse;
import com.album2me.repost.domain.album.model.Album;
import com.album2me.repost.domain.album.repository.AlbumRepository;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.room.service.RoomService;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final RoomService roomService;
    private final UserService userService;
    private final AlbumRepository albumRepository;

    public List<AlbumResponse> showAll() {
        final List<Album> albums = albumRepository.findAll();

        return albums.stream()
                .map(AlbumResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long create(final Long userId, final AlbumCreateRequest albumCreateRequest) {
        final User user = userService.findUserById(userId);
        final Room room = roomService.findRoomById(albumCreateRequest.roomId());

        final Long albumId = createAlbum(user, room, albumCreateRequest);

        return albumId;
    }

    private Long createAlbum(final User user, final Room room, final AlbumCreateRequest albumCreateRequest) {
        final Album newAlbum = albumCreateRequest.toEntity(user, room);

        return albumRepository.save(newAlbum)
                .getId();
    }

    @Transactional
    public void update(final Long id, final AlbumUpdateRequest albumUpdateRequest) {
        final Album album = findAlbumById(id);

        album.update(albumUpdateRequest.toEntity());
    }

    @Transactional
    public void delete(final Long id) {
        final Album album = findAlbumById(id);

        albumRepository.delete(album);
    }

    public Album findAlbumById(Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new NoSuchElementException("해당 id로 Album을 찾을 수 없습니다."));
    }
}
