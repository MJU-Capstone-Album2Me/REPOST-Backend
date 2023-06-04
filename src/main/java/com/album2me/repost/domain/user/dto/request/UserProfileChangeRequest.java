package com.album2me.repost.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UserProfileChangeRequest(
        @NotNull
        MultipartFile profileImage
) {
}
