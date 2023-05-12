package com.album2me.repost.domain.comment.service;

import com.album2me.repost.domain.comment.domain.Comment;
import com.album2me.repost.domain.comment.dto.request.CommentCreateRequest;
import com.album2me.repost.domain.comment.dto.request.CommentUpdateRequest;
import com.album2me.repost.domain.comment.repository.CommentRepository;
import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.post.service.PostService;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;

    @Transactional
    public Long create(final Long postId, final Long userId, final CommentCreateRequest commentCreateRequest) {
        final User user = userService.findUserById(userId);
        final Post post = postService.findPostById(postId);

        final Long commentId = createComment(user, post, commentCreateRequest);

        return commentId;
    }

    private Long createComment(final User user, final Post post, final CommentCreateRequest commentCreateRequest) {
        final Comment comment = commentCreateRequest.toEntity(user, post);

        return commentRepository.save(comment)
                .getId();
    }

    @Transactional
    public void update(final Long commentId, final Long userId, final CommentUpdateRequest commentUpdateRequest) {
        final Comment comment = findCommentById(commentId);
        final User user = userService.findUserById(userId);

        validateWriter(comment, user);

        comment.update(commentUpdateRequest.toEntity());
    }

    @Transactional
    public void delete(final Long commentId, final Long userId) {
        final Comment comment = findCommentById(commentId);
        final User user = userService.findUserById(userId);

        validateWriter(comment, user);

        commentRepository.delete(comment);
    }

    private void validateWriter(final Comment comment, final User user) {
        if (!comment.isWrittenBy(user)) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
    }

    public Comment findCommentById(final Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("해당 id로 Comment를 찾을 수 없습니다."));
    }
}
