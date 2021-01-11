package com.logycoconut.opgg.api.parser;

/**
 * @author hall
 * @description
 * @date 2021-01-11 22:45
 */
public interface Endpoint {

    String path();

    String parse();

    String request();

}
