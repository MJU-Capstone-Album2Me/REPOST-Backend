package com.album2me.repost.domain.comment.domain;

import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.user.model.User;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String contents;

    @Builder
    public Comment (
            final Long id,
            final Post post,
            final User user,
            final String contents
    ) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.contents = contents;
    }
}
