package com.engine.privatefood.api.otoapi;

import com.engine.privatefood.api.HttpParam;
import com.engine.privatefood.api.IBus;
import com.engine.privatefood.api.ParseJsonApi;
import com.engine.privatefood.api.ResponseType;
import com.engine.privatefood.bean.ShopBean;

/**
 * Created by engine on 16/3/12.
 */
public class LoadShopApi extends ParseJsonApi<ShopBean> {
    @HttpParam
    public double latitude ;
    @HttpParam
    public double lontitude ;
    @Override
    protected ResponseType getType() {
        return ResponseType.ARRAY;
    }

    @Override
    protected Class<ShopBean> getTClass() {
        return ShopBean.class;
    }

    @Override
    protected String getPath() {
        return "getshopList";
    }

    @Override
    protected IBus getResponse() {
        return this;
    }
}
