package com.logycoconut.opgg.api.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.logycoconut.opgg.api.entity.Champion;
import com.logycoconut.opgg.api.exception.GlobalException;
import com.logycoconut.opgg.api.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hall
 * @description 获取周免英雄
 * @date 2021-01-20 13:26
 */
@Slf4j
@Component
public class ChampionFreeEndpoint implements BaseEndpoint {

    @Override
    public String path() {
        return ROOT_PATH + "/champion/statistics";
    }

    @Override
    public String parse() {
        try {
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
        } catch (JsonProcessingException e) {
            log.info("Java Object to Json 失败:{}", "freeChampionList");
            throw new GlobalException(StatusCode.JSON_PROCESS_ERROR);
        } catch (Exception e) {
            log.info("全部英雄基本信息数据解析失败");
            throw new GlobalException(StatusCode.DOM_EXTRACT_ERROR);
        }
    }

}
