package com.engine.privatefood.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.engine.privatefood.R;
import com.engine.privatefood.bean.MenuBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by engine on 16/3/13.
 */
public class MenuAdapter extends OtoBaseAdapter<MenuBean> {
    public MenuAdapter(Context mContext, List<MenuBean> dataList) {
        super(mContext, dataList);
    }

    public void setListener(IChoiceMenu listener) {
        this.listener = listener;
    }

    private IChoiceMenu listener ;

    public interface IChoiceMenu{
        public void onMenuChoice(MenuBean menuBean);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final MenuBean bean = getItem(i);
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.menu_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.menuName.setText(bean.menuName);
        holder.price.setText(bean.price+"元");
        Picasso.with(mContext).load(bean.menuImage).into(holder.menuImage);
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (listener!=null){
                   listener.onMenuChoice(bean);
               }
            }
        });
        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'menu_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.menuImage)
        ImageView menuImage;
        @Bind(R.id.menuName)
        TextView menuName;
        @Bind(R.id.select)
        Button select;
        @Bind(R.id.price)
        TextView price ;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
