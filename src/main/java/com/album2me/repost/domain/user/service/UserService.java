package com.album2me.repost.domain.user.service;

import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void signUp(User user){
        userRepository.save(user);
    }
    public User findUserByAuthId(String authId){
        User user = userRepository.findByAuthId(authId)
                .orElseThrow(() -> new IllegalArgumentException(""));
        return user;
    }
}
