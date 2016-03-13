package com.engine.privatefood.api.otoapi;

import com.engine.privatefood.api.HttpParam;
import com.engine.privatefood.api.IBus;
import com.engine.privatefood.api.ParseJsonApi;
import com.engine.privatefood.api.ResponseType;
import com.engine.privatefood.bean.UserBean;

/**
 * Created by engine on 16/3/12.
 */
public class LoginApi extends ParseJsonApi<UserBean> {
    @HttpParam
    public String userName;
    @HttpParam
    public String password;
    @Override
    protected ResponseType getType() {
        return ResponseType.SINGLE;
    }

    @Override
    protected Class<UserBean> getTClass() {
        return UserBean.class;
    }

    @Override
    protected String getPath() {
        return "login";
    }

    @Override
    protected IBus getResponse() {
        return this;
    }
}
