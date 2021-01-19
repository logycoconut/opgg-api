package com.logycoconut.opgg.api.parser;

import com.logycoconut.opgg.api.entity.Champion;
import com.logycoconut.opgg.api.entity.ChampionRole;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
            return Jsoup.connect("https://www.op.gg" + this.path())
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
        List<Element> weekFreeChampionElements = document.select(".champion-index__champion-list .champion-index__champion-item__free")
                .stream()
                .map(element -> element.parent().parent())
                .collect(Collectors.toList());
        List<Champion> weekFreeChampions = getWeekFreeChampion(weekFreeChampionElements);

        // 获取英雄层级
        Elements championItems = document.select(".champion-trend-tier .champion-index-table tbody");
        Map<String, List<Champion>> champions = getChampionTier(championItems);

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
    public Map<String, List<Champion>> getChampionTier(Elements championItems) {

        Map<String, List<Champion>> roleChampionsMap = new HashMap<>(5);

        championItems.forEach(item -> {
            String role = item.className().substring(28);
            Elements champions = item.select("tr");
            List<Champion> championList = champions.stream()
                    .map(element -> {
                        ChampionRole championRole = new ChampionRole();

                        championRole.setNameZh(extractChampionName(element));
                        championRole.setWinRate(extractWinRate(element));
                        championRole.setPickRate(extractPickRate(element));
                        championRole.setTier(extractTier(element));
                        championRole.setRankingChange(extractRankingChanged(element));

                        return championRole;
                    })
                    .collect(Collectors.toList());

            roleChampionsMap.put(role, championList);
        });

        return roleChampionsMap;
    }

    /**
     * 获取周免英雄
     *
     * @param weekFreeChampionElements WeekFreeChampion Nodes
     * @return List<Champion>
     */
    public List<Champion> getWeekFreeChampion(List<Element> weekFreeChampionElements) {
        return weekFreeChampionElements.parallelStream()
                .map(element -> Champion.builder()
                        .nameEn(element.attr("data-champion-key"))
                        .nameZh(element.attr("data-champion-name"))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 提取 champion 的名次变化
     * @return changed ranking
     */
    private String extractRankingChanged(Element element) {
        String className = element.select(".champion-index-table__cell--change").attr("class");
        String rankingChanged = element.select(".champion-index-table__cell--change").text();
        String direction = className.substring(className.lastIndexOf("-") + 1);
        switch (direction) {
            case "up":
                rankingChanged = "+" + rankingChanged;
                break;
            case "down":
                rankingChanged = "-" + rankingChanged;
                break;
            default:
                break;
        }
        return rankingChanged;
    }

    /**
     * 提取 champion 的英文名
     * @return NameEn
     */
    private String extractChampionName(Element element) {
        return element.select(".champion-index-table__cell--champion .champion-index-table__name").text();
    }

    /**
     * 提取 champion 的胜率
     * @return Win Rate
     */
    private String extractWinRate(Element element) {
        return element.select(".champion-index-table__cell--value")
                .eq(0)
                .text();
    }

    /**
     * 提取 champion 的登场率
     * @return Pick Rate
     */
    private String extractPickRate(Element element) {
        return element.select(".champion-index-table__cell--value")
                .eq(1)
                .text();
    }

    /**
     * 提取 champion 的 tier
     * @return tier
     */
    private String extractTier(Element element) {
        String tierImgUrl = element.select(".champion-index-table__cell--value img")
                .attr("src");
        return "" + tierImgUrl.charAt(tierImgUrl.lastIndexOf(".png") - 1);
    }
}
