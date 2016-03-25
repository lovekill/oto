package com.engine.privatefood.bean;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by engine on 16/3/11.
 */
public class Location {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static Location instance ;
    public static Location getInstance(Context context){
        if (instance==null){
            instance=new Location(context);
        }
        return  instance;
    }
    private Location(Context context) {
        sharedPreferences = context.getSharedPreferences("location", 1);
        editor = sharedPreferences.edit();
    }
    public String address ;
    public double latitude ;
    public double lontitude ;
    public void save(){
        editor.putString("address",address);
        editor.putString("latitude",latitude+"");
        editor.putString("lontitude",lontitude+"");
        editor.commit();
    }
    public Location load(){
        this.address=sharedPreferences.getString("address","");
        this.latitude=Double.parseDouble(sharedPreferences.getString("latitude","0")) ;
        this.lontitude=Double.parseDouble(sharedPreferences.getString("lontitude","0")) ;
        return this ;
    }
}
