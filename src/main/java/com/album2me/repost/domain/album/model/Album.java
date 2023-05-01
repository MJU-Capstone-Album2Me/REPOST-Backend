package com.album2me.repost.domain.album.model;

import com.album2me.repost.global.common.BaseTimeColumn;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends BaseTimeColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private Long roomId;

    @Column(length = 20, unique = true, nullable = false)
    private Long writerId;

    @Column(length = 30, unique = true, nullable = false)
    private String name;

    @Column(length = 50)
    private String thumbnailUrl;

    @Column(length = 20)
    private Long postCount;

    @Builder
    public Album(
            final Long id,
            final Long roomId,
            final Long writerId,
            final String name,
            final String thumbnailUrl,
            final Long postCount
    ) {
        this.id = id;
        this.roomId = roomId;
        this.writerId = writerId;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.postCount = postCount;
    }
}
