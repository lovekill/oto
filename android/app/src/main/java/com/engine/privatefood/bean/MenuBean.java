package com.engine.privatefood.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by engine on 16/3/9.
 */
public class MenuBean implements Parcelable {
    public int menuId;
    public String menuName;
    public String menuImage ;
    public float price ;

    protected MenuBean(Parcel in) {
        menuId = in.readInt();
        menuName = in.readString();
        menuImage = in.readString();
        price = in.readFloat();
    }

    public static final Creator<MenuBean> CREATOR = new Creator<MenuBean>() {
        @Override
        public MenuBean createFromParcel(Parcel in) {
            return new MenuBean(in);
        }

        @Override
        public MenuBean[] newArray(int size) {
            return new MenuBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(menuId);
        parcel.writeString(menuName);
        parcel.writeString(menuImage);
        parcel.writeFloat(price);
    }
}
