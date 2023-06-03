package com.album2me.repost.domain.user.dto.request;

import com.album2me.repost.global.util.RegexUtils;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserCheckNicknameRequest(
        @NotNull
        @Pattern(regexp = RegexUtils.NICKNAME_REGEX, message = "닉네임은 한글, 숫자, 영어로 된 2~10자입니다.")
        String nickname
) {
}
