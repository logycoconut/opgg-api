package com.logycoconut.opgg.api.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.logycoconut.opgg.api.entity.Champion;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hall
 * @description 获取周免英雄
 * @date 2021-01-20 13:26
 */
@Slf4j
public class ChampionFreeEndpoint implements BaseEndpoint {

    @Override
    public String path() {
        return ROOT_PATH + "/champion/statistics";
    }

    @Override
    public String parse() throws JsonProcessingException {
        log.info("当前请求URL：{}", this.path());
        Document document = this.request();

        // 获取周免英雄
        List<Champion> freeChampionList = document
                .select(".champion-index__champion-list .champion-index__champion-item__free")
                .stream()
                .map(element -> {
                    Element championItem = element.parent().parent();
                    return Champion.builder()
                            .nameEn(championItem.attr("data-champion-key"))
                            .nameZh(championItem.attr("data-champion-name"))
                            .build();
                })
                .collect(Collectors.toList());
        log.info("周免英雄解析成功");

        return MAPPER.writeValueAsString(freeChampionList);
    }

}
