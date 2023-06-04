package com.album2me.repost.domain.comment.dto.response;

import jakarta.validation.constraints.NotNull;

public record CommentCreateResponse (
    @NotNull(message = "id가 없습니다.")
    Long id
) {

}
