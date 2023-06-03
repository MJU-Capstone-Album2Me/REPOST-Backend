package com.album2me.repost.domain.user.dto.request;

import com.album2me.repost.global.util.RegexUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserCheckIdRequest(
        @NotNull
        @Pattern(regexp = RegexUtils.AUTHID_REGEX, message = "아이디는 한글, 숫자, 영어로 된 4~20자입니다.")
        String authId
) {
}
