package com.album2me.repost.domain.reply.dto.response;

import com.album2me.repost.domain.reply.domain.Reply;

public record ReplyResponse (
        Long id,
        String contents
) {
    public static ReplyResponse from(final Reply reply) {
        return new ReplyResponse(reply.getId(), reply.getContents());
    }
}