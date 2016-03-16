package com.engine.privatefood.api.otoapi;

import com.engine.privatefood.api.HttpParam;
import com.engine.privatefood.api.IBus;
import com.engine.privatefood.api.NetApi;
import com.engine.privatefood.api.ParseJsonApi;

import java.util.List;

/**
 * Created by engine on 16/3/16.
 */
public class GeneratorOrderApi extends NetApi{
    @HttpParam
    public int userid ;
    @HttpParam
    public int shopid ;
    @HttpParam
    public int addressid ;
    @HttpParam
    public String menus ;
    @Override
    protected String getPath() {
        return "generatorOrder";
    }

    @Override
    protected IBus getResponse() {
        return this;
    }

    @Override
    public List getListModel() {
        return null;
    }

    @Override
    public Object getModel() {
        return null;
    }
}
