package com.album2me.repost.global.error;

import java.util.List;
import org.springframework.http.HttpStatus;

public record ErrorResponse(
        int status,
        int code,
        String message,
        List<FieldException> details
) {
    public record FieldException(
            String fieldName,
            String inputValue,
            String reason
    ){
        public static FieldException of(String fieldName, String inputValue, String reason){
            return new FieldException(fieldName, inputValue, reason);
        }
    }

    public static ErrorResponse badRequest(ErrorCode errorCode, List<FieldException> details){
        return new ErrorResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage(), details);
    }
}
