package com.logycoconut.opgg.api.exception;

import com.logycoconut.opgg.api.response.StatusCode;

/**
 * @author hall
 * @date 2021/1/20
 */
public class GlobalException extends RuntimeException {

    public GlobalException(StatusCode statusCode) {
        super(statusCode.getMessage());
    }

    public GlobalException(String message) {
        super(message);
    }
}
