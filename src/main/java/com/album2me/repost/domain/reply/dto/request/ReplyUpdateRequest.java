package com.album2me.repost.domain.reply.dto.request;

import com.album2me.repost.domain.reply.domain.Reply;
import jakarta.validation.constraints.NotNull;

public record ReplyUpdateRequest (
    @NotNull(message = "내용이 없습니다.") String contents
) {
    public Reply toEntity() {
        return Reply.builder()
                .contents(contents)
                .build();
    }
}
