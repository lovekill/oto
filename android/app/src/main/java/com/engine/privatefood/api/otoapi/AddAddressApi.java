package com.engine.privatefood.api.otoapi;

import com.engine.privatefood.api.HttpParam;
import com.engine.privatefood.api.IBus;
import com.engine.privatefood.api.ParseJsonApi;
import com.engine.privatefood.api.ResponseType;
import com.engine.privatefood.bean.AddressBean;

/**
 * Created by engine on 16/3/13.
 */
public class AddAddressApi extends ParseJsonApi<AddressBean> {
    @HttpParam
    public int userid;
    @HttpParam
    public String name ;
    @HttpParam
    public String phoneNumber ;
    @HttpParam
    public String address ;
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
        return "addAddress";
    }

    @Override
    protected IBus getResponse() {
        return this;
    }
}
