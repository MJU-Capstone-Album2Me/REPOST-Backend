package com.album2me.repost.domain.post.dto.response;

import com.album2me.repost.domain.post.model.Post;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public record PostPageResponse(
        List<PostResponse> items
) {
    public static PostPageResponse from(final Slice<Post> page) {
        final List<PostResponse> items = page.getContent().stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
        return new PostPageResponse(items);
    }
}
