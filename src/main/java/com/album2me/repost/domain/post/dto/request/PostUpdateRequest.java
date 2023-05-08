package com.album2me.repost.domain.post.dto.request;

import com.album2me.repost.domain.post.model.Post;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostUpdateRequest {
    @NotNull(message = "제목이 없습니다.")
    private String title;

    @NotNull(message = "내용이 없습니다.")
    private String contents;

    @Builder
    public Post toEntity() {
        return Post.builder()
                .title(title)
                .contents(contents)
                .build();
    }
}