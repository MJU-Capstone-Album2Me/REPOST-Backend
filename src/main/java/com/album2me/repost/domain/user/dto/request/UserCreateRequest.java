package com.album2me.repost.domain.user.dto.request;

import com.album2me.repost.domain.user.model.User;
import com.album2me.repost.global.util.RegexUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.crypto.password.PasswordEncoder;

public record UserCreateRequest(
        @NotNull
        @Pattern(regexp = RegexUtils.AUTHID_REGEX, message = "아이디는 한글, 숫자, 영어로 된 4~20자입니다.")
        String authId,
        @NotNull
        @Pattern(regexp = RegexUtils.PASSWORD_REGEX, message = "비밀번호는 숫자, 영어, 특수문자를 1개 이상 포함한 8~20자입니다.")
        String password,

        @NotNull
        @Pattern(regexp = RegexUtils.NICKNAME_REGEX, message = "닉네임은 한글, 숫자, 영어로 된 2~10자입니다.")
        String nickname
){

        public User toEntity(PasswordEncoder passwordEncoder) {
                return User.builder()
                        .authId(authId)
                        .password(passwordEncoder.encode(password))
                        .nickName(nickname)
                        .build();
        }
}
