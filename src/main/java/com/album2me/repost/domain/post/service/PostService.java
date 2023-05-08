package com.album2me.repost.domain.post.service;

import com.album2me.repost.domain.album.model.Album;
import com.album2me.repost.domain.album.service.AlbumService;
import com.album2me.repost.domain.post.dto.request.PostCreateRequest;
import com.album2me.repost.domain.post.dto.request.PostUpdateRequest;
import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.post.dto.response.PostResponse;
import com.album2me.repost.domain.post.repository.PostRepository;
import com.album2me.repost.domain.user.model.User;

import com.album2me.repost.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserService userService;
    private final AlbumService albumService;
    private final PostRepository postRepository;

    public PostResponse findById(final Long id) {

        final Post post = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return PostResponse.from(post);
    }

    @Transactional
    public Long create(final Long albumId, final Long userId, final PostCreateRequest postCreateRequest) {
        final User user = userService.findUserById(userId);
        final Album album = albumService.findAlbumById(albumId);

        final Long postId = createPost(user, album, postCreateRequest);

        return postId;
    }

    private Long createPost(final User user, final Album album, final PostCreateRequest postCreateRequest) {
        final Post post = postCreateRequest.toEntity(user, album);

        return postRepository.save(post)
                .getId();
    }

    @Transactional
    public void update(final Long id, final User user, final PostUpdateRequest postUpdateRequest) {
        final Post post = findPostById(id);

        validateWriter(post, user);

        post.update(postUpdateRequest.toEntity());
    }

    private void validateWriter(final Post post, final User user) {
        if (!post.isWrittenBy(user)) {
                throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("해당 id로 Post을 찾을 수 없습니다."));
    }
}
