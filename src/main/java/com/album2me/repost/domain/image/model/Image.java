package com.album2me.repost.domain.image.model;

import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.global.common.BaseTimeColumn;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseTimeColumn {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String postImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public Image (
            final Long id,
            final String postImageUrl,
            final Post post
    ) {
        this.id = id;
        this.postImageUrl = postImageUrl;
        this.post = post;
    }
}
