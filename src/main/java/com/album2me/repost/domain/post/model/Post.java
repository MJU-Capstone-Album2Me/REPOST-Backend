package com.album2me.repost.domain.post.model;

import com.album2me.repost.domain.comment.domain.Comment;
import com.album2me.repost.domain.image.model.Image;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.global.common.BaseTimeColumn;

import jakarta.persistence.*;

import lombok.*;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Image> images = new ArrayList<>();

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 200)
    private String contents;

    @Column(length = 10)
    @ColumnDefault("0")
    private Long commentCount;

    @Column(length = 1)
    @ColumnDefault("false")
    private boolean isFavorite;

    @Builder
    public Post (
            final Long id,
            final Room room,
            final User user,
            final String title,
            final String contents,
            final Long commentCount,
            final boolean isFavorite
    ) {
        this.id = id;
        this.room = room;
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.commentCount = commentCount;
        this.isFavorite = isFavorite;
    }

    public void update(final Post post) {
        updateTitle(post.getTitle());
        updateContents(post.getContents());
    }

    private void updateTitle(final String title) {
        if (title != null) {
            this.title = title;
        }
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
        if (!(o instanceof Post)) {
            return false;
        }
        final Post post = (Post) o;
        return Objects.equals(id, post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


