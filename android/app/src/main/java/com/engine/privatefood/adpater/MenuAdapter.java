package com.engine.privatefood.adpater;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.baidu.mapapi.map.Text;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
    }

    private ViewGroup rootView ;
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
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                TextView textView = new TextView(mContext);
                textView.setText("+1");
                textView.setTextColor(Color.YELLOW);
                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                p.setMargins(location[0],location[1],0,0);
                rootView.addView(textView,p);
                Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.out_to_bottom);
                textView.startAnimation(animation);
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
