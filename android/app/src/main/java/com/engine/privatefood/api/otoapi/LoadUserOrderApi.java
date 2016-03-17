package com.engine.privatefood.api.otoapi;

import com.engine.privatefood.api.HttpParam;
import com.engine.privatefood.api.IBus;
import com.engine.privatefood.api.ParseJsonApi;
import com.engine.privatefood.api.ResponseType;
import com.engine.privatefood.bean.OrderBean;

/**
 * Created by engine on 16/3/17.
 */
public class LoadUserOrderApi extends ParseJsonApi<OrderBean>{
    @HttpParam
    public int userid ;

    @Override
    protected ResponseType getType() {
        return ResponseType.ARRAY;
    }

    @Override
    protected Class<OrderBean> getTClass() {
        return OrderBean.class;
    }

    @Override
    protected String getPath() {
        return "getOrderListByUser";
    }

    @Override
    protected IBus getResponse() {
        return this;
    }
}
