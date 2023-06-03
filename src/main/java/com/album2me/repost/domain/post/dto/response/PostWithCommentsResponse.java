package com.album2me.repost.domain.post.dto.response;

import com.album2me.repost.domain.comment.dto.response.CommentDto;
import com.album2me.repost.domain.image.dto.ImageDto;
import com.album2me.repost.domain.post.model.Post;

import java.util.List;

public record PostWithCommentsResponse(
        Long id,
        String title,
        String nickname,
        String profileImageUrl,
        String contents,
        List<ImageDto> images,
        List<CommentDto> comments
) {

    public static PostWithCommentsResponse from(final Post post, final List<ImageDto> images, final List<CommentDto> comments) {
        return new PostWithCommentsResponse(
                post.getId(),
                post.getTitle(),
                post.getUser().getNickName(),
                post.getUser().getProfileImageUrl(),
                post.getContents(),
                images,
                comments
        );
    }
}
