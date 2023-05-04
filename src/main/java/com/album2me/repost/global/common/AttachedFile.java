package com.album2me.repost.global.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class AttachedFile {
    private static final String UNIX_SEPARATOR = "/";
    private static final String EXTENSION_SEPARATOR = ".";
    private static final String DEFAULT_EXTENSION = "jpeg";
    private final String fileName;
    private final InputStream inputStream;
    private final String contentType;
    private final long contentLength;

    public AttachedFile(String fileName, InputStream inputStream, String contentType, long contentLength) {
        this.fileName = fileName;
        this.inputStream = inputStream;
        this.contentType = contentType;
        this.contentLength = contentLength;
    }

    public static Optional<AttachedFile> toAttachedFile(String basePath, MultipartFile multipartFile) {
        try{
            if(verify(multipartFile)){
                String generatedFileName = generateRandomName(basePath, multipartFile.getOriginalFilename());
                return Optional.of(new AttachedFile(
                        generatedFileName, multipartFile.getInputStream(), multipartFile.getContentType(), multipartFile.getSize())
                );
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("");
        }
        return Optional.empty();
    }

    private static boolean verify(MultipartFile multipartFile) {
        if(multipartFile != null && multipartFile.getSize() > 0 && multipartFile.getOriginalFilename() != null) {
            String contentType = multipartFile.getContentType();
            return !contentType.isEmpty() && contentType.toLowerCase().startsWith("image");
        }
        return false;
    }

    private static String generateRandomName(String bathPath, String originalName) {
        String fileName = bathPath + UNIX_SEPARATOR + UUID.randomUUID()
                + EXTENSION_SEPARATOR + getExtension(originalName);
        return fileName;
    }

    private static String getExtension(String originalName) {
        String extension = StringUtils.getFilenameExtension(originalName);
        return extension.isEmpty() ? DEFAULT_EXTENSION : extension;
    }
}
