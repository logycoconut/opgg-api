package com.logycoconut.opgg.api.service;

import com.logycoconut.opgg.api.endpoint.ChampionEndpoint;
import com.logycoconut.opgg.api.endpoint.ChampionFreeEndpoint;
import com.logycoconut.opgg.api.endpoint.ChampionTierEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hall
 * @description
 * @date 2021-01-11 22:45
 */
@Component
public class ParserService {

    @Autowired
    private ChampionEndpoint championEndpoint;

    @Autowired
    private ChampionFreeEndpoint championFreeEndpoint;

    @Autowired
    private ChampionTierEndpoint championTierEndpoint;

    public String getChampions() {
        return championEndpoint.parse();
    }

    public String getFreeChampions() {
        return championFreeEndpoint.parse();
    }

    public String getTierChampions() {
        return championTierEndpoint.parse();
    }

}
