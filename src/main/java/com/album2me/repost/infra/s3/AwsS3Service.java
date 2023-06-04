package com.album2me.repost.infra.s3;

import com.album2me.repost.global.common.AttachedFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsS3Service {
    private static final String UNIX_SEPARATOR = "/";
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.s3.url}")
    private String url;
    private final AmazonS3 amazonS3;

    public String uploadImage(AttachedFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getContentLength());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, file.getFileName(), file.getInputStream(), objectMetadata);
        amazonS3.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
        return generateUrl(file.getFileName());
    }

    private String generateUrl(String fileName) {
        StringBuilder sb = new StringBuilder(url);
        if (!url.endsWith(UNIX_SEPARATOR))
            sb.append(UNIX_SEPARATOR);
        sb.append(bucketName);
        sb.append(UNIX_SEPARATOR);
        sb.append(fileName);
        return sb.toString();
    }

    public void deleteImage(final String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}
