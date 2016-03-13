package com.engine.privatefood.api.otoapi;

import com.engine.privatefood.api.HttpParam;
import com.engine.privatefood.api.IBus;
import com.engine.privatefood.api.ParseJsonApi;
import com.engine.privatefood.api.ResponseType;
import com.engine.privatefood.bean.MenuBean;

/**
 * Created by engine on 16/3/13.
 */
public class LoadMenuApi extends ParseJsonApi<MenuBean> {
    @HttpParam
    public int shopid ;
    @Override
    protected ResponseType getType() {
        return ResponseType.ARRAY;
    }

    @Override
    protected Class<MenuBean> getTClass() {
        return MenuBean.class;
    }

    @Override
    protected String getPath() {
        return "getMenuList";
    }

    @Override
    protected IBus getResponse() {
        return this;
    }
}
