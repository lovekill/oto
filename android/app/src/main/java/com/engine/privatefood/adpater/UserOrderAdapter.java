package com.engine.privatefood.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.engine.privatefood.R;
import com.engine.privatefood.bean.OrderBean;
import com.engine.privatefood.bean.OrderSectionBean;
import com.engine.privatefood.view.PinnedSectionListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by engine on 16/3/17.
 */
public class UserOrderAdapter extends OtoBaseAdapter<OrderSectionBean> implements PinnedSectionListView.PinnedSectionListAdapter {
    private Map<Integer, String> map = new HashMap<Integer, String>();

    public UserOrderAdapter(Context mContext, List<OrderSectionBean> dataList) {
        super(mContext, dataList);
        map.put(1, "已下单");
        map.put(2, "已接单");
        map.put(3, "已送出");
        map.put(4, "已完成");
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OrderSectionBean bean = getItem(i);
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.order_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (bean.type==OrderSectionBean.ITEM) {
            holder.address.setText(bean.orderBean.address);
            holder.orderNumber.setText(bean.orderBean.orderNumber);
            holder.price.setText(bean.orderBean.price + "元");
            holder.time.setText(bean.orderBean.time);
            holder.status.setText(map.get(bean.orderBean.status));
            holder.section.setVisibility(View.GONE);
            holder.contentLayout.setVisibility(View.VISIBLE);
        }else {
            holder.section.setText(bean.orderBean.sectionTime);
            holder.section.setVisibility(View.VISIBLE);
            holder.contentLayout.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).type;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType==OrderSectionBean.SECTION;
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
        @Bind(R.id.section)
        TextView section;
        @Bind(R.id.contentLayout)
        RelativeLayout contentLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
