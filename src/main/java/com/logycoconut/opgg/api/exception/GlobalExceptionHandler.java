package com.logycoconut.opgg.api.exception;

import com.logycoconut.opgg.api.response.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author hall
 * @date 2021/1/20
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public Result<Void> exceptionHandler(Exception e) {
        // todo JsonProcessingException
        return null;
    }

}
