package com.album2me.repost.domain.comment.domain;

import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.user.model.User;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    public void update(final Comment comment) {
        updateContents(comment.getContents());
    }

    private void updateContents(final String contents) {
        if (contents != null) {
            this.contents = contents;
        }
    }

    public boolean isWrittenBy(final User user) {
        return this.user.equals(user);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment comment)) {
            return false;
        }
        return Objects.equals(id, comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
