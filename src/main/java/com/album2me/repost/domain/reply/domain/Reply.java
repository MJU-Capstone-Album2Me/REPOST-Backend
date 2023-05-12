package com.album2me.repost.domain.reply.domain;

import com.album2me.repost.domain.comment.domain.Comment;
import com.album2me.repost.domain.user.model.User;

import com.album2me.repost.global.common.BaseTimeColumn;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTimeColumn {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column(nullable = false)
    private String contents;

    @Builder
    public Reply (
            final Long id,
            final User user,
            final Comment comment,
            final String contents
    ) {
        this.id = id;
        this.user = user;
        this.comment = comment;
        this.contents = contents;
    }

    public boolean isWrittenBy(final User user) {
        return this.user.equals(user);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reply reply)) {
            return false;
        }
        return Objects.equals(id, reply.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
