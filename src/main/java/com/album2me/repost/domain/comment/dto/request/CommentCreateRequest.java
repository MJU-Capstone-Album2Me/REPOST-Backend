package com.album2me.repost.domain.comment.dto.request;

import com.album2me.repost.domain.comment.domain.Comment;

import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.user.model.User;

import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest (
    @NotNull(message = "댓글 내용이 없습니다.")
    String contents
) {
    public Comment toEntity(final User user, final Post post) {
        return Comment.builder()
                .user(user)
                .post(post)
                .contents(contents)
                .build();
    }

}
