package com.logycoconut.opgg.api.parser;

import com.logycoconut.opgg.api.enums.Role;
import com.logycoconut.opgg.api.entity.ChampionRole;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author hall
 * @description 获取全部英雄基本信息、常用位置的层级、周免英雄
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
        Elements elements = document.select(".champion-index__champion-list .champion-index__champion-item");

        elements.parallelStream()
                .forEach(
                        element -> {
                            // TODO 根据element得到Champion对象和ChampionRole对象
                        }
                );

        return null;
    }

    /**
     * 根据 class 名生成 ChampionRole 对象
     * @param classNames 类名
     * @return ChampionRole列表
     */
    private List<ChampionRole> generateRelations(Set<String> classNames) {
        classNames.remove("champion-index__champion-item");
        classNames.forEach(className -> {
            ChampionRole championRole = new ChampionRole();
            championRole.setChampionNameEn("");
            // TODO 细节需要重构
            if ("champion-index__champion-item--MID" .equals(className)) {
                championRole.setRole(Role.MID);
            }
        });
        return null;
    }

}
