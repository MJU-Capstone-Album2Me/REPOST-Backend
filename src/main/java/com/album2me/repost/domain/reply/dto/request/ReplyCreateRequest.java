package com.album2me.repost.domain.reply.dto.request;

import com.album2me.repost.domain.comment.domain.Comment;
import com.album2me.repost.domain.reply.domain.Reply;
import com.album2me.repost.domain.user.model.User;
import jakarta.validation.constraints.NotNull;

public record ReplyCreateRequest (
    @NotNull(message = "내용이 없습니다.") String contents
) {
    public Reply toEntity(final User user, final Comment comment) {
        return Reply.builder()
                .user(user)
                .comment(comment)
                .contents(contents)
                .build();
    }
}
