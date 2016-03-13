package com.engine.privatefood.adpater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.engine.privatefood.R;
import com.engine.privatefood.bean.ShopBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by engine on 16/3/12.
 */
public class ShopAdatper extends OtoBaseAdapter<ShopBean> {
    public ShopAdatper(Context mContext, List<ShopBean> dataList) {
        super(mContext, dataList);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ShopBean shopBean = getItem(i);
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.shop_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.shopName.setText(shopBean.shopName);
        holder.maxAmount.setText(shopBean.maxAmount+"");
        holder.limitAmount.setText(shopBean.limitAmount+"");
        holder.subtrackPrice.setText(shopBean.subtrackPrice+"");
        Picasso.with(mContext).load(shopBean.shopImage).into(holder.shopImage);
        return view;
    }

    static

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'shop_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */

    class ViewHolder {
        @Bind(R.id.shopImage)
        ImageView shopImage;
        @Bind(R.id.shopName)
        TextView shopName;
        @Bind(R.id.limitAmount)
        TextView limitAmount;
        @Bind(R.id.limitAmountLayout)
        LinearLayout limitAmountLayout;
        @Bind(R.id.maxAmount)
        TextView maxAmount;
        @Bind(R.id.subtrackPrice)
        TextView subtrackPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
