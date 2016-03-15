package com.engine.privatefood.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by engine on 16/3/9.
 */
public class ShopBean implements Parcelable{
    public int shopid ;
    public String shopName ;
    public String shopImage ;
    public String phoneNumber;
    public String address ;
    public double latitude ;
    public  double lontitude ;
    public float limitAmount ;
    public float maxAmount ;
    public float subtrackPrice;

    protected ShopBean(Parcel in) {
        shopid = in.readInt();
        shopName = in.readString();
        shopImage = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        latitude = in.readDouble();
        lontitude = in.readDouble();
        limitAmount = in.readFloat();
        maxAmount = in.readFloat();
        subtrackPrice = in.readFloat();
    }

    public static final Creator<ShopBean> CREATOR = new Creator<ShopBean>() {
        @Override
        public ShopBean createFromParcel(Parcel in) {
            return new ShopBean(in);
        }

        @Override
        public ShopBean[] newArray(int size) {
            return new ShopBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(shopid);
        parcel.writeString(shopName);
        parcel.writeString(shopImage);
        parcel.writeString(phoneNumber);
        parcel.writeString(address);
        parcel.writeDouble(latitude);
        parcel.writeDouble(lontitude);
        parcel.writeFloat(limitAmount);
        parcel.writeFloat(maxAmount);
        parcel.writeFloat(subtrackPrice);
    }
}
