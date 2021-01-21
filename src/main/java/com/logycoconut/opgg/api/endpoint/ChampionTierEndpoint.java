package com.logycoconut.opgg.api.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.logycoconut.opgg.api.entity.ChampionRole;
import com.logycoconut.opgg.api.exception.GlobalException;
import com.logycoconut.opgg.api.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hall
 * @description 获取各位置的热门英雄与层级
 * @date 2021-01-20 13:51
 */
@Slf4j
@Component
public class ChampionTierEndpoint implements BaseEndpoint {

    @Override
    public String path() {
        return ROOT_PATH + "/champion/statistics";
    }

    @Override
    public String parse() {
        log.info("当前请求URL：{}", this.path());
        Document document = this.request(this.path());

        try {
            // 获取英雄层级
            Map<String, List<ChampionRole>> roleChampionsMap = new HashMap<>(5);
            document.select(".champion-trend-tier .champion-index-table tbody")
                    .forEach(positionItem -> {
                        // 构建 role - champions Map
                        String role = positionItem.className().substring(28).toLowerCase();
                        Elements championItemElements = positionItem.select("tr");
                        List<ChampionRole> championList = championItemElements
                                .stream()
                                .map(itemElement -> ChampionRole.builder()
                                        .nameZh(extractChampionName(itemElement))
                                        .winRate(extractWinRate(itemElement))
                                        .pickRate(extractPickRate(itemElement))
                                        .tier(extractTier(itemElement))
                                        .rankingChange(extractRankingChanged(itemElement)).build()
                                )
                                .collect(Collectors.toList());
                        roleChampionsMap.put(role, championList);
                    });

            log.info("英雄T级解析成功");
            return MAPPER.writeValueAsString(roleChampionsMap);
        } catch (JsonProcessingException e) {
            log.info("Java Object to Json 失败:{}", "roleChampionsMap");
            throw new GlobalException(StatusCode.JSON_PROCESS_ERROR);
        } catch (Exception e) {
            log.info("全部英雄基本信息数据解析失败");
            throw new GlobalException(StatusCode.DOM_EXTRACT_ERROR);
        }
    }

    /**
     * 提取 champion 的名次变化
     *
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
     *
     * @return NameEn
     */
    private String extractChampionName(Element element) {
        return element.select(".champion-index-table__cell--champion .champion-index-table__name").text();
    }

    /**
     * 提取 champion 的胜率
     *
     * @return Win Rate
     */
    private String extractWinRate(Element element) {
        return element.select(".champion-index-table__cell--value")
                .eq(0)
                .text();
    }

    /**
     * 提取 champion 的登场率
     *
     * @return Pick Rate
     */
    private String extractPickRate(Element element) {
        return element.select(".champion-index-table__cell--value")
                .eq(1)
                .text();
    }

    /**
     * 提取 champion 的 tier
     *
     * @return tier
     */
    private String extractTier(Element element) {
        String tierImgUrl = element.select(".champion-index-table__cell--value img")
                .attr("src");
        return "" + tierImgUrl.charAt(tierImgUrl.lastIndexOf(".png") - 1);
    }
}
