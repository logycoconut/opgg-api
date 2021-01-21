package com.logycoconut.opgg.api.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.logycoconut.opgg.api.entity.ChampionForm;
import com.logycoconut.opgg.api.exception.GlobalException;
import com.logycoconut.opgg.api.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

/**
 * @author hall
 * @description 获取英雄天赋
 * @date 2021-01-20 13:26
 */
@Slf4j
@Component
public class ChampionRunesEndpoint implements BaseEndpoint {

    @Override
    public String path() {
        return ROOT_PATH + "/champion/:champion/statistics/:role/rune";
    }

    @Override
    public String parse(ChampionForm champion) {
        try {
            String path = this.path().replace(":champion", champion.getNameEn())
                    .replace(":role", champion.getRole());
            log.info("当前请求URL：{}", path);
            Document document = this.request(path);

            // 获取英雄天赋


            log.info("英雄天赋解析成功");
            return MAPPER.writeValueAsString(null);
        } catch (JsonProcessingException e) {
            log.info("Java Object to Json 失败:{}", "freeChampionList");
            throw new GlobalException(StatusCode.JSON_PROCESS_ERROR);
        } catch (Exception e) {
            log.info("全部英雄基本信息数据解析失败");
            throw new GlobalException(StatusCode.DOM_EXTRACT_ERROR);
        }
    }

}
