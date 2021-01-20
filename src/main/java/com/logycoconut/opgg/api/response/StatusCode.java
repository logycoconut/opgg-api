package com.logycoconut.opgg.api.response;

import lombok.Getter;

/**
 * @author hall
 * @date 2021/1/20
 */
@Getter
public enum StatusCode {

    /**
     * 状态码
     */

    SUCCESS(200, "ok"),
    FAILED(500, "failed"),
    CONNECT_ERROR(500001, "源文档请求失败"),
    JSON_PROCESS_ERROR(500002, "Java Object to Json 失败"),
    DOM_EXTRACT_ERROR(500003, "Java Object to Json 失败"),


    ;

    private final Integer code;
    private final String message;

    StatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
