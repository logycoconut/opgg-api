package com.logycoconut.opgg.api.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author hall
 * @description 英雄角色信息
 * @date 2021-01-13 22:18
 */
@Data
@Builder
public class ChampionRole {

    private String nameZh;
    private String nameEn;
    private String tier;
    private String winRate;
    private String pickRate;
    private String rankingChange;

}
