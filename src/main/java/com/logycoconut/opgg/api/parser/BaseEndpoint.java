package com.logycoconut.opgg.api.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logycoconut.opgg.api.exception.GlobalException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author hall
 * @description 网页解析接口
 * @date 2021-01-11 22:45
 */
public interface BaseEndpoint {

    /**
     * 请求根路径
     */
    String ROOT_PATH = "https://" + "www.op.gg";

    /**
     * Jackson
     */
    ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 定义数据路径
     *
     * @return 数据所在网页地址
     */
    String path();

    /**
     * 解析方法
     *
     * @return 解析后的数据
     */
    String parse() throws JsonProcessingException;

    /**
     * 通用请求方法
     *
     * @return dom文档
     */
    default Document request() {
        try {
            return Jsoup.connect(this.path())
                    .header("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                    .timeout(60000)
                    .get();
        } catch (IOException exception) {
            throw new GlobalException(exception.getMessage());
        }
    }

}
