package com.album2me.repost.domain.reply.service;

import com.album2me.repost.domain.comment.domain.Comment;
import com.album2me.repost.domain.comment.service.CommentService;
import com.album2me.repost.domain.reply.domain.Reply;
import com.album2me.repost.domain.reply.dto.request.ReplyCreateRequest;
import com.album2me.repost.domain.reply.dto.request.ReplyUpdateRequest;
import com.album2me.repost.domain.reply.repository.ReplyRepository;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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

        final Long replyId = createReply(comment, user, replyCreateRequest);

        return replyId;
    }

    private Long createReply(final Comment comment, final User user, final ReplyCreateRequest replyCreateRequest) {
        final Reply reply = replyCreateRequest.toEntity(user, comment);

        return replyRepository.save(reply)
                .getId();
    }

    @Transactional
    public void update(final Long replyId, final Long userId, final ReplyUpdateRequest replyUpdateRequest) {
        final Reply reply = findReplyById(replyId);
        final User user = userService.findUserById(userId);

        validateWriter(reply, user);

        reply.update(replyUpdateRequest.toEntity());
    }

    @Transactional
    public void delete(final Long replyId, final Long userId) {
        final Reply reply = findReplyById(replyId);
        final User user = userService.findUserById(userId);

        validateWriter(reply, user);

        replyRepository.delete(reply);
    }

    private void validateWriter(final Reply reply, final User user) {
        if (!reply.isWrittenBy(user)) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
    }

    public Reply findReplyById(final Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(() -> new NoSuchElementException("해당 id로 Reply를 찾을 수 없습니다."));
    }
}
