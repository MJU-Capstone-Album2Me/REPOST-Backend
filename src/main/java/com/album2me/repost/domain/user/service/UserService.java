package com.album2me.repost.domain.user.service;

import com.album2me.repost.domain.image.dto.UploadImageRequest;
import com.album2me.repost.domain.image.dto.UploadImageResponse;
import com.album2me.repost.domain.image.service.ImageService;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;

    public void signUp(User user){
        userRepository.save(user);
    }

    public User findUserByAuthId(final String authId){
        return userRepository.findByAuthId(authId)
                .orElseThrow(() -> new NoSuchElementException(""));
    }

    public User findUserById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 id로 User를 찾을 수 없습니다."));
    }

    public void updateProfileImage(final Long userId, final UploadImageRequest uploadImageRequest) {
        final UploadImageResponse uploadImageResponse = imageService.uploadImageToS3(uploadImageRequest);
        final User user = findUserById(userId);

        user.updateProfileImage(uploadImageResponse.imageUrl());
    }
}
