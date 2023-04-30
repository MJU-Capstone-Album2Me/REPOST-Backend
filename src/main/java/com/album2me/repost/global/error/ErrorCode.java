package com.album2me.repost.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //COMMON
    INVALID_ARGUMENT(400, 1000, "유효하지 않은 입력.");

    private final int status;
    private final int code;
    private final String message;

    ErrorCode(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
