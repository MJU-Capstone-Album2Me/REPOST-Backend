package com.album2me.repost.domain.reply.dto.response;

import com.album2me.repost.domain.reply.domain.Reply;
import jakarta.validation.constraints.NotNull;

public record ReplyDto(
    @NotNull(message = "id가 없습니다.")
    Long id,

    @NotNull(message = "content가 없습니다.")
    String content,

    @NotNull(message = "nickname이 없습니다.")
    String nickname,

    @NotNull(message = "profile_image_url이 없습니다.")
    String profileImageUrl
) {

    public static ReplyDto from(final Reply reply) {
        return new ReplyDto(
            reply.getId(),
            reply.getContents(),
            reply.getUser().getNickName(),
            reply.getUser().getProfileImageUrl()
        );

    }
}
