package com.logycoconut.opgg.api.parser;

/**
 * @author hall
 * @description
 * @date 2021-01-11 22:53
 */
public class ChampionEndpoint implements Endpoint{

    @Override
    public String path() {
        return "/champion/{champion}/statistics/{role}";
    }

    @Override
    public String parse() {
        return null;
    }

    @Override
    public String request() {
        return null;
    }

}
