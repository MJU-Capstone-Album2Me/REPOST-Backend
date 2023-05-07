package com.album2me.repost.domain.post.service;

import com.album2me.repost.domain.album.model.Album;
import com.album2me.repost.domain.album.repository.AlbumRepository;
import com.album2me.repost.domain.post.dto.request.PostCreateRequest;
import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.post.dto.response.PostResponse;
import com.album2me.repost.domain.post.repository.PostRepository;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    public PostResponse findById(final Long id) {

        final Post post = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return PostResponse.from(post);
    }

    @Transactional
    public Long create(final Long userId, final PostCreateRequest postCreateRequest) {
        final User user = findUserById(userId);
        final Album album = findAlbumById(postCreateRequest.getAlbumId());

        final Long postId = createPost(user, album, postCreateRequest);

        return postId;
    }

    private Long createPost(final User user, final Album album, final PostCreateRequest postCreateRequest) {
        final Post post = postCreateRequest.toEntity(user, album);

        return postRepository.save(post)
                .getId();
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 id로 User를 찾을 수 없습니다."));
    }

    private Album findAlbumById(Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new NoSuchElementException("해당 id로 Album을 찾을 수 없습니다."));
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("해당 id로 Post을 찾을 수 없습니다."));
    }
}
