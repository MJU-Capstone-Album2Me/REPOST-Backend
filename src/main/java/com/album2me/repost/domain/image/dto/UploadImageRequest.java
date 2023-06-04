package com.album2me.repost.domain.image.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UploadImageRequest(
        @NotNull(message = "파일이 존재하지 않습니다.")
        MultipartFile image
) {
        public static UploadImageRequest of(final MultipartFile image) {
                return new UploadImageRequest(image);
        }

}
