package com.album2me.repost.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //Common
    INVALID_ARGUMENT(400, 1000, "유효하지 않은 입력."),
    NOT_FOUND(404, 2000, "요청한 리소스를 찾을 수 없음.");

    private final int status;
    private final int code;
    private final String message;

    ErrorCode(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
