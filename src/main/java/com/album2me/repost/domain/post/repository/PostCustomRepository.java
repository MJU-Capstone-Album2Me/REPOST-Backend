package com.album2me.repost.domain.post.repository;

import com.album2me.repost.domain.post.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostCustomRepository {
    Slice<Post> findAllPostWithImage(Long roomId, Long cursorId, Pageable pageable);
}
