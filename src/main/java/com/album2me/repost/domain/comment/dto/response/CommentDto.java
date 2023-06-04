package com.album2me.repost.domain.comment.dto.response;

import com.album2me.repost.domain.comment.domain.Comment;
import com.album2me.repost.domain.reply.dto.response.ReplyDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CommentDto(
    @NotNull(message = "id가 없습니다.")
    Long id,
    @NotNull(message = "nickname이 없습니다.")
    String nickname,

    @NotNull(message = "profileImageUrl가 없습니다.")
    String profileImageUrl,

    @NotNull(message = "content가 없습니다.")
    String contents,

    List<ReplyDto> replies
) {

    public static CommentDto from(final Comment comment, final List<ReplyDto> replies) {
        return new CommentDto(
                comment.getId(),
                comment.getUser().getNickName(),
                comment.getUser().getProfileImageUrl(),
                comment.getContents(),
                replies
        );
    }
}
