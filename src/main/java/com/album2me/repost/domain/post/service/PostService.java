package com.album2me.repost.domain.post.service;

import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.post.dto.response.PostResponse;
import com.album2me.repost.domain.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponse findById(final Long id) {

        final Post post = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return PostResponse.from(post);
    }
}
