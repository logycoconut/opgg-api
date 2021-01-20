package com.logycoconut.opgg.api.response;

import lombok.Data;

/**
 * @author hall
 * @date 2021/1/20
 */
@Data
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public Result(T data) {
        this.data = data;
    }

    public Result(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }

    public Result(StatusCode statusCode, T data) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(StatusCode.SUCCESS, data);
    }

    public static Result<Void> error(StatusCode statusCode) {
        return new Result<>(statusCode);
    }

}
