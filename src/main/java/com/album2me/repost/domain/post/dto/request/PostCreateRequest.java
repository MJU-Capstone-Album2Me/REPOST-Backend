package com.album2me.repost.domain.post.dto.request;

import com.album2me.repost.domain.post.model.Post;
import com.album2me.repost.domain.room.model.Room;
import com.album2me.repost.domain.user.model.User;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostCreateRequest (
        @NotNull(message = "제목이 없습니다.") String title,

        String contents,

        @NotNull(message = "사진이 없습니다.") List<MultipartFile> images
) {

    public Post toEntity(final User user, final Room room) {
        return Post.builder()
                .user(user)
                .room(room)
                .title(title)
                .contents(contents)
                .build();
    }

    public List<MultipartFile> getImages() {
        return this.images;
    }
}