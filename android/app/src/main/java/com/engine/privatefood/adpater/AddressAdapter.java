package com.engine.privatefood.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.engine.privatefood.R;
import com.engine.privatefood.bean.AddressBean;

import java.util.List;

/**
 * Created by engine on 16/3/13.
 */
public class AddressAdapter extends OtoBaseAdapter<AddressBean> {
    public AddressAdapter(Context mContext, List<AddressBean> dataList) {
        super(mContext, dataList);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AddressBean bean = getItem(i);
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.address_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.address.setText(bean.address);
        holder.name.setText(bean.name);
        holder.phoneNumber.setText(bean.phoneNumber);
        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'address_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.phoneNumber)
        TextView phoneNumber;
        @Bind(R.id.address)
        TextView address;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
