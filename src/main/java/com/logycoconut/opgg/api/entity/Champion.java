package com.logycoconut.opgg.api.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author hall
 * @description 英雄基本信息
 * @date 2021-01-13 21:42
 */
@Data
@Builder
public class Champion {

    private String nameZh;
    private String nameEn;
    private Boolean rip;
    private String commonPositions;

}
