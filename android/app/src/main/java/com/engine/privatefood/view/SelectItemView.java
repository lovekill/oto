package com.engine.privatefood.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.engine.privatefood.R;
import com.engine.privatefood.bean.MenuBean;

/**
 * Created by engine on 16/3/14.
 */
public class SelectItemView extends LinearLayout {
    @Bind(R.id.menuName)
    TextView menuName;
    @Bind(R.id.price)
    TextView price;
    MenuBean bean;

    public void setListener(ISelectListener listener) {
        this.listener = listener;
    }

    private  ISelectListener listener;

    public interface ISelectListener{
        public void onMenuRemove(MenuBean bean);
    }

    public SelectItemView(Context context, MenuBean menuBean) {
        super(context);
        init(context,menuBean);
        this.bean=bean ;
    }

    public void init(Context context,MenuBean menuBean) {
        LayoutInflater.from(context).inflate(R.layout.select_item, this);
        ButterKnife.bind(this,this);
        menuName.setText(menuBean.menuName);
        price.setText(menuBean.price+"元");
    }

    @OnClick(R.id.delete)
    public void onDelete(View view){
       if(listener!=null){
            listener.onMenuRemove(bean);
       }
    }
}
