package com.engine.privatefood.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.engine.privatefood.R;
import com.engine.privatefood.bean.OrderBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by engine on 16/3/17.
 */
public class UserOrderAdapter extends OtoBaseAdapter<OrderBean> {
    private Map<Integer,String> map = new HashMap<Integer, String>();
    public UserOrderAdapter(Context mContext, List<OrderBean> dataList) {
        super(mContext, dataList);
        map.put(1,"已下单");
        map.put(2,"已接单");
        map.put(3,"已送出");
        map.put(4,"已完成");
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OrderBean bean = getItem(i);
        ViewHolder holder =null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.order_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.address.setText(bean.address);
        holder.orderNumber.setText(bean.orderNumber);
        holder.price.setText(bean.price+"元");
        holder.time.setText(bean.time);
        holder.status.setText(map.get(bean.status));
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.orderNumber)
        TextView orderNumber;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.address)
        TextView address;
        @Bind(R.id.status)
        TextView status;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
