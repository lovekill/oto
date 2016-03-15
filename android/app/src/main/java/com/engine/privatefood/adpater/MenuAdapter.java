package com.engine.privatefood.adpater;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
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
import com.engine.privatefood.activity.BaseActivity;
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
    private ActionBar actionBar ;
    public void setRootView(ViewGroup rootView,ActionBar actionBar) {
        this.rootView = rootView;
        this.actionBar=actionBar ;
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
                Rect frame = new Rect();
                ((BaseActivity)mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                view.getLocationInWindow(location);
                TextView textView = new TextView(mContext);
                textView.setText("+1");
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundResource(R.drawable.add_shap);
                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                p.setMargins(location[0]+view.getMeasuredWidth()/2,location[1]-actionBar.getHeight()-frame.top+view.getMeasuredHeight()/2,0,0);
                rootView.addView(textView,p);
                YoYo.with(Techniques.SlideOutDown).duration(200).playOn(textView);
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
