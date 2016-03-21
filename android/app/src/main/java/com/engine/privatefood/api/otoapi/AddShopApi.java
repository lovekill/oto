package com.engine.privatefood.api.otoapi;

import com.engine.privatefood.api.*;
import com.engine.privatefood.bean.ShopBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by engine on 16/3/21.
 */
public class AddShopApi extends ParseJsonApi<ShopBean> {
    @HttpParam
    public int userid ;
    @HttpParam
    public String shopName ;
    @HttpParam
    public String userName ;
    @HttpParam
    public String phoneNumber ;
    @HttpParam
    public String address ;
    @HttpParam
    public float limitAmount ;
    @HttpParam
    public float maxAmount ;
    @HttpParam
    public float subtrackPrice ;
    @HttpParam
    public double latitude ;
    @HttpParam
    public double lontitud ;
    public List<File> fileList = new ArrayList<File>();
    @Override
    protected ResponseType getType() {
        return ResponseType.SINGLE;
    }

    @Override
    protected Class<ShopBean> getTClass() {
        return ShopBean.class;
    }

    @Override
    protected String getPath() {
        return "addShop";
    }

    @Override
    protected IBus getResponse() {
        return this;
    }

    @Override
    protected Method getMethod() {
        return Method.FORM;
    }

    public void addFile(File file){
        fileList.add(file);
    }

    @Override
    public List<File> getFileList() {
        return fileList;
    }
}
