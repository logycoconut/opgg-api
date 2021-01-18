package com.logycoconut.opgg.api.parser;

import org.jsoup.nodes.Document;

/**
 * @author hall
 * @description 网页解析接口
 * @date 2021-01-11 22:45
 */
public interface BaseEndpoint {

    /**
     * 定义数据路径
     * @return 数据所在网页地址
     */
    String path();

    /**
     * 解析方法
     * @return 解析后的数据
     */
    String parse();

    /**
     * 通用请求方法
     * @return dom文档
     */
    Document request();

}
