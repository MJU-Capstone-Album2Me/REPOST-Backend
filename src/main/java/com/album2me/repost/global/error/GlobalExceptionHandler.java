package com.album2me.repost.global.error;

import com.album2me.repost.global.error.ErrorResponse.FieldException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.debug("Bad request exception occurred: {}", e.getMessage(), e);
        List<FieldException> details = createFieldExceptionList(e.getBindingResult());
        return ErrorResponse.badRequest(ErrorCode.INVALID_ARGUMENT, details);
    }

    private List<FieldException> createFieldExceptionList(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream().map(fieldError -> FieldException.of(
                fieldError.getField(),
                fieldError.getRejectedValue() == null ? null : fieldError.getRejectedValue().toString(),
                fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
    }

}
