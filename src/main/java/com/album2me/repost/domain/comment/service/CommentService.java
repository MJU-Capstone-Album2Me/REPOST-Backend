package com.album2me.repost.domain.comment.service;

import com.album2me.repost.domain.comment.domain.Comment;
import com.album2me.repost.domain.comment.dto.request.CommentCreateRequest;
import com.album2me.repost.domain.comment.repository.CommentRepository;
import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.post.service.PostService;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
