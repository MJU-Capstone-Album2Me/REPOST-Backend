package com.album2me.repost.domain.post.dto.response;

import com.album2me.repost.domain.post.model.Post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.beans.BeanUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

    private Long id;
    private Long writerId;
    private Long albumId;
    private String title;
    private String contents;
    private Long commentCount;
    private boolean isFavorite;

    public static PostResponse from(final Post post) {
        final PostResponse postResponse = new PostResponse();
        BeanUtils.copyProperties(post, postResponse);

        return postResponse;
    }
}
