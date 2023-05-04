package com.album2me.repost.domain.image.controller;

import com.album2me.repost.domain.image.dto.UploadImageRequest;
import com.album2me.repost.domain.image.dto.UploadImageResponse;
import com.album2me.repost.domain.image.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UploadImageResponse uploadImage(@ModelAttribute @Valid UploadImageRequest uploadImageRequest) {
        UploadImageResponse uploadImageResponse = imageService.uploadImage(uploadImageRequest);
        return uploadImageResponse;
    }
}
