package com.logycoconut.opgg.api.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.logycoconut.opgg.api.entity.Champion;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hall
 * @description 获取全部英雄基本信息
 * @date 2021-01-11 22:53
 */
@Slf4j
@Component
public class ChampionEndpoint implements BaseEndpoint {

    @Override
    public String path() {
        return ROOT_PATH + "/champion/statistics";
    }

    @Override
    public String parse() throws JsonProcessingException {
        log.info("当前请求URL：{}", this.path());
        Document document = this.request();

        // 获取所有英雄
        List<Champion> championList = document
                .select(".champion-index__champion-list .champion-index__champion-item")
                .stream()
                .map(element -> {
                    String nameZh = element.attr("data-champion-name");
                    String nameEn = element.attr("data-champion-key");

                    // 判断该英雄是否有足够样本 (rest in peace)
                    boolean rip = element.childNodeSize() != 1;

                    // 常用位置
                    String commonPositions = "";
                    if (!rip) {
                        commonPositions = element.select(".champion-index__champion-item__positions")
                                .stream()
                                .map(Element::text)
                                .reduce("", (acc, n) -> acc + n);
                    }

                    return Champion.builder()
                            .nameZh(nameZh)
                            .nameEn(nameEn)
                            .rip(rip)
                            .commonPositions(commonPositions)
                            .build();
                })
                .collect(Collectors.toList());


        return MAPPER.writeValueAsString(championList);
    }

}
