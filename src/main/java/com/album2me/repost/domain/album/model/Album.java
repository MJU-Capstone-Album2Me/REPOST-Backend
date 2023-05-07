package com.album2me.repost.domain.album.model;

import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.global.common.BaseTimeColumn;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends BaseTimeColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "album", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @Column(length = 30, unique = true, nullable = false)
    private String name;

    @Column(length = 50)
    private String thumbnailUrl;

    @Column(length = 20, nullable = false)
    private int postCount;

    @Builder
    public Album(
            final Long id,
            final Room room,
            final User user,
            final String name,
            final String thumbnailUrl,
            final int postCount
    ) {
        this.id = id;
        this.room = room;
        this.user = user;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.postCount = postCount;
    }

    public void update(final Album album) {
        updateName(album.getName());
        updateThumbnail(album.getThumbnailUrl());
    }

    private void updateName(final String name) {
        if (name != null) {
            this.name = name;
        }
    }

    private void updateThumbnail(final String thumbnailUrl) {
        if (thumbnailUrl != null) {
            this.thumbnailUrl = thumbnailUrl;
        }
    }
}
