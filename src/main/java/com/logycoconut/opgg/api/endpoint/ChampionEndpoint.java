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
    public String parse() {
        log.info("当前请求URL：{}", this.path());
        Document document = this.request(this.path());

        try {
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

            log.info("全部英雄基本信息数据解析成功");
            return MAPPER.writeValueAsString(championList);
        } catch (JsonProcessingException e) {
            log.info("Java Object to Json 失败:{}", "championList");
            throw new GlobalException(StatusCode.JSON_PROCESS_ERROR);
        } catch (Exception e) {
            log.info("全部英雄基本信息数据解析失败");
            throw new GlobalException(StatusCode.DOM_EXTRACT_ERROR);
        }
    }

}
