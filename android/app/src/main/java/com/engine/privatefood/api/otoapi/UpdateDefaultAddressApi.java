package com.engine.privatefood.api.otoapi;

import com.engine.privatefood.api.HttpParam;
import com.engine.privatefood.api.IBus;
import com.engine.privatefood.api.NetApi;

import java.util.List;

/**
 * Created by engine on 16/3/16.
 */
public class UpdateDefaultAddressApi extends NetApi{
    @HttpParam
    public int userid ;
    @HttpParam
    public int addressid ;
    @Override
    protected String getPath() {
        return "setDefaultAddress";
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
