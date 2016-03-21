package com.engine.privatefood.api;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.engine.privatefood.BusProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cat1412266 on 15/7/26.
 */
public abstract class NetApi extends AsyncTask<String, String, String> implements IBus {

    //公司
    protected static String BASE_URL = "http://192.168.2.169:8061/oto/";
    //家里
//    protected static String BASE_URL="http://api.grayweb.cn:8061/oto/";
//    protected static String BASE_URL="http://192.168.1.103:8061/oto/";
    protected String TAG = getClass().getSimpleName();
    private OkHttpClient client = new OkHttpClient();
    protected Gson gson;
    public static final MediaType JSON = MediaType.parse("application/text; charset=utf-8");
    String json = "";

    public String getJsonStr() {
        return json;
    }

    public String doGet() throws IOException {

        Map<String, Object> map = getHttpParam();
        StringBuilder sb = new StringBuilder(BASE_URL);
        sb.append(getPath());
        if (!map.isEmpty()) {
            sb.append("?");
            sb.append(getParamString(map));
        }
        Log.e(TAG, "GET URL:" + sb.toString());

        Request request = new Request.Builder().url(sb.toString()).build();
        Response response = client.newCall(request).execute();


        return response.body().string();
    }

    private String getParamString(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key).append("=").append(map.get(key)).append("&");
        }

        return sb.toString();
    }

    private String doPost() throws IOException {
        String json = paramForJson();
        Log.e(TAG, "POST URL:" + BASE_URL + getPath() + ":" + json);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(BASE_URL + getPath())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public NetApi() {
//    if(Looper.myLooper() == Looper.getMainLooper()) {
//      BusProvider.getBus().register(this);
//    }
    }

    protected List<File> getFileList() {
        return new ArrayList<File>();
    }

    static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");

    private String doFormPost() throws IOException {
        Map<String, Object> map = getHttpParam();
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        for (String key : map.keySet()) {
            if (map.get(key) != null) {
                builder.addFormDataPart(key, map.get(key).toString());
            }
        }
        for (int i = 0; i < getFileList().size(); i++) {
            builder.addFormDataPart("fileArray", "eee", RequestBody.create(MEDIA_TYPE_JPG, getFileList().get(0)));
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(BASE_URL + getPath())//地址
                .post(requestBody)//添加请求体
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            return response.body().string();
        } else {
            Log.e(TAG, String.format("code=%d message=%s", response.code(), response.message()));
            return null;
        }

    }

    private String paramForJson() {
        JSONObject jsonObject = new JSONObject(getHttpParam());
        return jsonObject.toString();
    }

    protected Method getMethod() {
        return Method.GET;
    }

    protected abstract String getPath();


    protected abstract IBus getResponse();


    protected void requestEnd(String response) {
        if (response != null) {
            BusProvider.getBus().register(this);
            BusProvider.getBus().post(getResponse());
            BusProvider.getBus().unregister(this);
        } else {
            Log.e(TAG, "Fail");
            BusProvider.getBus().register(this);
            FailResponse failResponse = new FailResponse();
            NetErrorModel model = new NetErrorModel();
            model.errorDesc = "网络异常";
            failResponse.netErrorModel = model;
            BusProvider.getBus().post(failResponse);
            BusProvider.getBus().unregister(this);
        }
    }

    protected void requestFail() {
        BusProvider.getBus().register(this);
        FailResponse failResponse = new FailResponse();
        NetErrorModel model = new NetErrorModel();
        model.errorDesc = "服务器异常,请稍后再试";
        failResponse.netErrorModel = model;
        BusProvider.getBus().post(failResponse);
        BusProvider.getBus().unregister(this);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String result;
            if (getMethod() == Method.GET) {
                result = doGet();
            } else {
                result = doFormPost();
            }
            if (result != null) {
                json = result;
                Log.e(TAG, result);
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (gson == null) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        requestEnd(s);
    }


    private Map<String, Object> getHttpParam() {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        Field[] fileds = getClass().getDeclaredFields();
        fieldsToMap(fileds, paraMap);
        if (getClass().getSuperclass() != null) {
            Field[] fs = getClass().getSuperclass().getDeclaredFields();
            fieldsToMap(fs, paraMap);
        }
        return paraMap;
    }

    private void fieldsToMap(@NonNull Field[] fileds, @NonNull Map<String, Object> map) {
        for (int i = 0; i < fileds.length; i++) {
            if (fileds[i].getAnnotation(HttpParam.class) != null) {
                try {
                    map.put(fileds[i].getName(), fileds[i].get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
