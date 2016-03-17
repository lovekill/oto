package com.engine.privatefood.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by engine on 16/3/17.
 */
public class OrderBean implements Parcelable {
    public int orderid ;
    public String orderNumber ;
    public String time ;
    public String shopName ;
    public String address ;
    public float price ;
    public int status ;

    protected OrderBean(Parcel in) {
        orderid = in.readInt();
        orderNumber = in.readString();
        time = in.readString();
        shopName = in.readString();
        address = in.readString();
        price = in.readFloat();
        status = in.readInt();
    }

    public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>() {
        @Override
        public OrderBean createFromParcel(Parcel in) {
            return new OrderBean(in);
        }

        @Override
        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(orderid);
        parcel.writeString(orderNumber);
        parcel.writeString(time);
        parcel.writeString(shopName);
        parcel.writeString(address);
        parcel.writeFloat(price);
        parcel.writeInt(status);
    }
}
