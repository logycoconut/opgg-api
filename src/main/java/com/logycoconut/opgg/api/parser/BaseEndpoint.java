package com.logycoconut.opgg.api.parser;

import org.jsoup.nodes.Document;

/**
 * @author hall
 * @description
 * @date 2021-01-11 22:45
 */
public interface BaseEndpoint {

    String path();

    String parse();

    Document request();

}
