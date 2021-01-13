package com.logycoconut.opgg.api.entity;

import com.logycoconut.opgg.api.constraint.Role;
import lombok.Data;

/**
 * @author hall
 * @description 英雄与角色对应关系
 * @date 2021-01-13 22:18
 */
@Data
public class ChampionRole {

    private Long id;
    private String championNameEn;
    private Role role;
    private String level;
    private String rank;

}
