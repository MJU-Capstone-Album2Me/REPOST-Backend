package com.album2me.repost.domain.post.model;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long roomId;

    @Column(nullable = false)
    private Long writerId;

    @Column(nullable = false)
    private String title;

    private String contents;

    @Column(length = 10)
    private Long commentCount;

    @Column(length = 1)
    @ColumnDefault("false")
    private boolean isFavorite;
}
