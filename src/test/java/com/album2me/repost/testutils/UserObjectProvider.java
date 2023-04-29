package com.album2me.repost.testutils;

import com.album2me.repost.domain.user.dto.UserCreateRequest;
import com.album2me.repost.domain.user.model.User;

public class UserObjectProvider {
    private static String authId = "repost1234";
    private static String password = "repost1234!";
    private static String nickname = "repost";

    public static User createUser(){
        User user = User.builder()
                .authId(authId)
                .password(password)
                .nickName(nickname)
                .build();
        return user;
    }

    public static UserCreateRequest userCreateRequest() {
        return new UserCreateRequest(authId, password, nickname);
    }
}
