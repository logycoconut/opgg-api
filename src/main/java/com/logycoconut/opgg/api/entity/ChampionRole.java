package com.logycoconut.opgg.api.entity;

import com.logycoconut.opgg.api.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hall
 * @description 英雄角色信息
 * @date 2021-01-13 22:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChampionRole extends Champion{

    private Role role;
    private String tier;
    private String winRate;
    private String pickRate;
    private String rankingChange;

}
