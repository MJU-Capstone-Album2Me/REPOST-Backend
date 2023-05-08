package com.album2me.repost.domain.user.service;

import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
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
}
