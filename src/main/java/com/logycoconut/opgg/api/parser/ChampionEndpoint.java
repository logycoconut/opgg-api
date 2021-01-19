package com.logycoconut.opgg.api.parser;

import com.logycoconut.opgg.api.entity.Champion;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hall
 * @description 获取全部英雄基本信息、各位置的热门英雄与层级、周免英雄
 * @date 2021-01-11 22:53
 */
@Slf4j
public class ChampionEndpoint implements BaseEndpoint {

    @Override
    public String path() {
        return "/champion/statistics";
    }

    @Override
    public Document request() {
        try {
            return Jsoup.connect("https://www.op.gg/" + this.path())
                    .header("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                    .timeout(60000)
                    .get();
        } catch (IOException exception) {
            return null;
        }
    }

    @Override
    public String parse() {
        Document document = this.request();
        Assert.notNull(document, "请求文档为空，请检查是否超时");

        // 获取周免英雄
        Elements weekFreeChampions = document.select(".champion-index__champion-list .champion-index__champion-item__free")
                .parents()
                .parents();
        List<Champion> weekFreeChampion = extractWeekFreeChampion(weekFreeChampions);

        // 获取英雄层级
        Elements championItems = document.select(".tabItem champion-trend champion-trend-tier .champion-index-table tabItems tbody");
        Map<String, List<Champion>> champions = extractChampionTier(championItems);

        // 获取所有英雄
        Elements elements = document.select(".champion-index__champion-list .champion-index__champion-item");


        return null;
    }

    /**
     * 获取英雄层级
     *
     * @param championItems champion组，对应五个位置
     * @return Map<String, List < Champion>>
     */
    public Map<String, List<Champion>> extractChampionTier(Elements championItems) {

        Map<String, List<Champion>> roleChampionsMap = new HashMap<>(5);

        championItems.forEach(item -> {
            String role = item.className().substring(8);
            Elements champions = item.select("tr");
            List<Champion> championList = champions.stream()
                    .map(element -> {
                        // todo 记录英雄中英文名、胜率、层级、较上次排名上升/下降名次
                        Champion champion = new Champion();

                        element.select(".champion-index-table__cell champion-index-table__cell--champion champion-index-table__name").text();

                        return champion;
                    })
                    .collect(Collectors.toList());

            roleChampionsMap.put(role, championList);
        });

        return roleChampionsMap;
    }

    /**
     * 获取周免英雄
     *
     * @param elements WeekFreeChampion Nodes
     * @return List<Champion>
     */
    public List<Champion> extractWeekFreeChampion(Elements elements) {
        return elements.parallelStream()
                .map(element -> {
                    Champion champion = new Champion();
                    champion.setNameEn(element.attr("data-champion-key"));
                    champion.setNameZh(element.attr("data-champion-name"));
                    return champion;
                })
                .collect(Collectors.toList());
    }
}
