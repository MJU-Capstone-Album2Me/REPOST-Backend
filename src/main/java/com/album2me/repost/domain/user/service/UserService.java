package com.album2me.repost.domain.user.service;

import com.album2me.repost.domain.user.dto.request.UserCheckIdRequest;
import com.album2me.repost.domain.user.dto.request.UserCheckNicknameRequest;
import com.album2me.repost.domain.user.dto.response.UserCheckResponse;
import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void signUp(User user){
        userRepository.save(user);
    }

    public User findUserByAuthId(final String authId){
        return userRepository.findByAuthId(authId)
                .orElseThrow(() -> new NoSuchElementException(""));

    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 id로 User를 찾을 수 없습니다."));
    }

    public UserCheckResponse checkIdDuplicated(UserCheckIdRequest userCheckIdRequest) {
        String authId = userCheckIdRequest.authId();
        if(userRepository.existsByAuthId(authId)){
            return new UserCheckResponse(false);
        }
        return new UserCheckResponse(true);
    }

    public UserCheckResponse checkNicknameDuplicated(UserCheckNicknameRequest userCheckNicknameRequest) {
        String nickname = userCheckNicknameRequest.nickname();
        if(userRepository.existsByNickName(nickname)){
            return new UserCheckResponse(false);
        }
        return new UserCheckResponse(true);
    }
}
