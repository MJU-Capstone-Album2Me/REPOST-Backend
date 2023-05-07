package com.album2me.repost.domain.post.model;

import com.album2me.repost.domain.album.model.Album;
import com.album2me.repost.domain.comment.domain.Comment;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.global.common.BaseTimeColumn;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String title;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @Column(length = 200)
    private String contents;

    @Column(length = 10)
    private Long commentCount;

    @Column(length = 1)
    @ColumnDefault("false")
    private boolean isFavorite;

    @Builder
    public Post (
            final Long id,
            final Album album,
            final User user,
            final String title,
            final String contents,
            final Long commentCount,
            final boolean isFavorite
    ) {
        this.id = id;
        this.album = album;
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.commentCount = commentCount;
        this.isFavorite = isFavorite;
   }
}


