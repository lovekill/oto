package com.engine.privatefood.api.otoapi;

import com.engine.privatefood.api.HttpParam;
import com.engine.privatefood.api.IBus;
import com.engine.privatefood.api.ParseJsonApi;
import com.engine.privatefood.api.ResponseType;
import com.engine.privatefood.bean.OrderDetailBean;

/**
 * Created by engine on 16/3/17.
 */
public class LoadOrderDetailApi extends ParseJsonApi<OrderDetailBean>{
    @HttpParam
    public int orderid ;

    @Override
    protected ResponseType getType() {
        return ResponseType.SINGLE;
    }

    @Override
    protected Class<OrderDetailBean> getTClass() {
        return OrderDetailBean.class;
    }

    @Override
    protected String getPath() {
        return "getOrderDetail";
    }

    @Override
    protected IBus getResponse() {
        return this;
    }
}
