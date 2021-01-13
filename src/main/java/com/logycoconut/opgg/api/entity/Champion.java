package com.logycoconut.opgg.api.entity;

import lombok.Data;

/**
 * @author hall
 * @description 英雄类
 * @date 2021-01-13 21:42
 */
@Data
public class Champion {

    private Long id;
    private String nameZh;
    private String nameEn;
    private String imageUrl;

}
