package com.logycoconut.opgg.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hall
 * @description 英雄基本信息
 * @date 2021-01-13 21:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Champion {

    private String nameZh;
    private String nameEn;

}
