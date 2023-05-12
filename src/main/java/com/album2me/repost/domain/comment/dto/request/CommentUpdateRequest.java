package com.album2me.repost.domain.comment.dto.request;

import com.album2me.repost.domain.comment.domain.Comment;
import jakarta.validation.constraints.NotNull;

public record CommentUpdateRequest (
    @NotNull(message = "내용이 없습니다.")
    String contents
) {
    public Comment toEntity() {
        return Comment.builder()
                .contents(contents)
                .build();
    }
}
