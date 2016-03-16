package com.engine.privatefood.api.otoapi;

import com.engine.privatefood.api.HttpParam;
import com.engine.privatefood.api.IBus;
import com.engine.privatefood.api.ParseJsonApi;
import com.engine.privatefood.api.ResponseType;
import com.engine.privatefood.bean.AddressBean;

/**
 * Created by engine on 16/3/16.
 */
public class GetDefaultAddressApi extends ParseJsonApi<AddressBean> {
    @HttpParam
    public int userid ;
    @Override
    protected ResponseType getType() {
        return ResponseType.SINGLE;
    }

    @Override
    protected Class<AddressBean> getTClass() {
        return AddressBean.class;
    }

    @Override
    protected String getPath() {
        return "getDefaultAddress";
    }

    @Override
    protected IBus getResponse() {
        return this;
    }
}
