package com.engine.privatefood.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by engine on 15/11/6.
 * 解析Bean的API
 */
public abstract class ParseJsonApi<T> extends NetApi {
    private List<T> list ;
    private  T t  ;
    protected abstract ResponseType getType();
    protected abstract Class<T> getTClass() ;
    @Override
    protected void requestEnd(String response) {
        boolean flag = false ;
        if(response!=null){
           flag= parseJson(response);
        }
        if (flag) {
            super.requestEnd(response);
        }else {
            requestFail();
        }
    }

    @Override
    public final List<T> getListModel() {
        return list;
    }

    @Override
    public final T getModel() {
        return t;
    }

    public void parseEnd(T model) {

    }
    public void parseEnd(List<T> list) {

    }

    public boolean parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String code = jsonObject.optString("code");
            if (code.equals("0")) {
                if (getType() == ResponseType.ARRAY) {
                    list = fromArray(jsonObject.optJSONArray("data"));
                    parseEnd(list);
                } else {
                    t = fromJson(jsonObject.optJSONObject("data"));
                    parseEnd(t);
                }
                return true ;
            }else {
                return  false ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false ;
        }
    }

    public List<T> fromArray(JSONArray array) {
        List<T> list = new ArrayList<T>();
        if (array!=null) {
            for (int i = 0; i < array.length(); i++) {
                T t = gson.fromJson(array.opt(i).toString(), getTClass());
                list.add(t);
            }
        }
        return list;
    }

    protected T fromJson(JSONObject json) {
        T t = null;
        if(json!=null){
            t = gson.fromJson(json.toString(), getTClass());
        }

        return t;
    }
}
