package com.album2me.repost.domain.comment.dto.response;

import com.album2me.repost.domain.comment.domain.Comment;

public record CommentResponse (
    Long id,
    String contents
) {
    public static CommentResponse from(final Comment comment) {
        return new CommentResponse(comment.getId(), comment.getContents());
    }
}
