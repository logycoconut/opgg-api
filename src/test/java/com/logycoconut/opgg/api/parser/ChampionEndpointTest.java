package com.logycoconut.opgg.api.parser;

import com.logycoconut.opgg.api.endpoint.ChampionEndpoint;
import com.logycoconut.opgg.api.endpoint.ChampionFreeEndpoint;
import com.logycoconut.opgg.api.endpoint.ChampionTierEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author hall
 * @date 2021/1/20
 */
@SpringBootTest
class ChampionEndpointTest {

    @Autowired
    private ChampionEndpoint championEndpoint;

    @Autowired
    private ChampionFreeEndpoint championFreeEndpoint;

    @Autowired
    private ChampionTierEndpoint championTierEndpoint;

    @Test
    void parse() {
        String parse = championTierEndpoint.parse();
        System.out.println(parse);
    }
}