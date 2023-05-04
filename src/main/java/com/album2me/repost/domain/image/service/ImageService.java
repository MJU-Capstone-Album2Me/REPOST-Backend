package com.album2me.repost.domain.image.service;

import static com.album2me.repost.global.common.AttachedFile.toAttachedFile;

import com.album2me.repost.domain.image.dto.UploadImageRequest;
import com.album2me.repost.domain.image.dto.UploadImageResponse;
import com.album2me.repost.global.common.AttachedFile;
import com.album2me.repost.infra.s3.AwsS3Service;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final AwsS3Service awsS3Service;

    public UploadImageResponse uploadImage(UploadImageRequest uploadImageRequest){
        AttachedFile attachedFile = getAttachedFile(uploadImageRequest.image());
        String imageUrl;
        try{
            imageUrl = awsS3Service.uploadImage(attachedFile);
        } catch (AmazonS3Exception e) {
            throw new RuntimeException("");
        }
        return new UploadImageResponse(imageUrl);
    }

    private AttachedFile getAttachedFile(MultipartFile multipartFile) {
        return toAttachedFile("profile", multipartFile).orElseThrow(IllegalArgumentException::new);
    }

}
