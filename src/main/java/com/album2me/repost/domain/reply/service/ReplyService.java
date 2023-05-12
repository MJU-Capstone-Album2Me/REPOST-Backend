package com.album2me.repost.domain.reply.service;

import com.album2me.repost.domain.comment.domain.Comment;
import com.album2me.repost.domain.comment.service.CommentService;
import com.album2me.repost.domain.reply.domain.Reply;
import com.album2me.repost.domain.reply.dto.request.ReplyCreateRequest;
import com.album2me.repost.domain.reply.repository.ReplyRepository;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final UserService userService;
    private final CommentService commentService;
    private final ReplyRepository replyRepository;

    @Transactional
    public Long create(final Long commentId, final Long userId, final ReplyCreateRequest replyCreateRequest) {
        final User user = userService.findUserById(userId);
        final Comment comment = commentService.findCommentById(commentId);

        final Long replyId = createReply(user, comment, replyCreateRequest);

        return replyId;
    }

    private Long createReply(final User user, final Comment comment, final ReplyCreateRequest replyCreateRequest) {
        final Reply reply = replyCreateRequest.toEntity(user, comment);

        return replyRepository.save(reply)
                .getId();
    }

}
