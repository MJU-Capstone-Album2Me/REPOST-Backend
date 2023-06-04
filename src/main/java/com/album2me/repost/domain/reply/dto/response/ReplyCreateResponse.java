package com.album2me.repost.domain.reply.dto.response;

import jakarta.validation.constraints.NotNull;

public record ReplyCreateResponse(
    @NotNull(message = "id가 없습니다.")
    Long id
) {

}
