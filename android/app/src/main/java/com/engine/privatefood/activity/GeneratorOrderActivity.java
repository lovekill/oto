package com.engine.privatefood.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.engine.privatefood.R;
import com.engine.privatefood.bean.MenuBean;
import com.engine.privatefood.bean.ShopBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by engine on 16/3/15.
 */
public class GeneratorOrderActivity extends BaseActivity {
    public static final String MENUS = "menus";
    public static final String SHOP = "shop" ;
    List<MenuBean> list;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.menuItem)
    LinearLayout menuItem;
    @Bind(R.id.totalPrice)
    TextView totalPrice;
    @Bind(R.id.realPrice)
    TextView realPrice;
    @Bind(R.id.subtrackPrice)
    TextView subtrackPrice;
    ShopBean shopBean ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator_order);
        ButterKnife.bind(this);
        list = getIntent().getParcelableArrayListExtra(MENUS);
        shopBean = getIntent().getParcelableExtra(SHOP);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("确定订单");
        initMenu();
    }
    public void initMenu(){
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        float totalPrice = 0f ;
        for (MenuBean menuBean:list){
            View item = LayoutInflater.from(this).inflate(R.layout.menu_order_item,null);
            TextView menuName = (TextView) item.findViewById(R.id.menuName);
            TextView menuPrice = (TextView)item.findViewById(R.id.price);
            menuName.setText(menuBean.menuName);
            menuPrice.setText(menuBean.price+"元");
            menuItem.addView(item);
            totalPrice+=menuBean.price;
        }
    }
}
