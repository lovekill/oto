package com.engine.privatefood.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.engine.privatefood.R;
import com.engine.privatefood.api.otoapi.LoadOrderDetailApi;
import com.engine.privatefood.bean.MenuBean;
import com.engine.privatefood.bean.OrderBean;
import com.engine.privatefood.bean.OrderDetailBean;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by engine on 16/3/17.
 */
public class OrderDetailActivity extends BaseActivity {
    @Bind(R.id.shopName)
    TextView shopName;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.nameLayout)
    LinearLayout nameLayout;
    @Bind(R.id.phoneNumber)
    TextView phoneNumber;
    @Bind(R.id.phoneLayout)
    LinearLayout phoneLayout;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.addressLayout)
    RelativeLayout addressLayout;
    @Bind(R.id.menuItem)
    LinearLayout menuItem;
    @Bind(R.id.totalPrice)
    TextView totalPrice;
    @Bind(R.id.subtrackPrice)
    TextView subtrackPrice;
    @Bind(R.id.submit)
    Button submit;

    public static final String ORDER = "order";
    OrderBean orderBean;
    @Bind(R.id.shopPhoneNumber)
    TextView shopPhoneNumber;
    @Bind(R.id.orderStatus)
    TextView orderStatus;
    private Map<Integer, String> map = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        orderBean = getIntent().getParcelableExtra(ORDER);
        getOrderDetail();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("订单详情");
        actionBar.setDisplayHomeAsUpEnabled(true);
        map.put(1, "已下单");
        map.put(2, "已接单");
        map.put(3, "已送出");
        map.put(4, "已完成");
    }

    public void getOrderDetail() {
        LoadOrderDetailApi api = new LoadOrderDetailApi();
        api.orderid = orderBean.orderid;
        api.execute();
    }

    @Subscribe
    public void onOrderDetailResponse(LoadOrderDetailApi api) {
        OrderDetailBean orderDetailBean = api.getModel();
        initMenuItem(orderDetailBean.menues);
        shopName.setText(orderDetailBean.order.shopName);
        name.setText(orderDetailBean.order.personName);
        phoneNumber.setText(orderDetailBean.order.phoneNumber);
        totalPrice.setText(orderDetailBean.order.price + "元");
        address.setText(orderDetailBean.order.address);
        subtrackPrice.setText(orderDetailBean.order.subtrackPrice + "元");
        shopPhoneNumber.setText(orderDetailBean.order.shopPhoneNumber);
        orderStatus.setText(map.get(orderDetailBean.order.status));
        if (orderDetailBean.order.status<3){
            submit.setText("取消订单");
            submit.setEnabled(true);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }else {
           submit.setText("稍后即送达");
            submit.setEnabled(false);
        }
    }

    public void initMenuItem(List<MenuBean> menuList) {
        for (MenuBean bean : menuList) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.menu_order_item, null);
            TextView menuName = (TextView) itemView.findViewById(R.id.menuName);
            TextView price = (TextView) itemView.findViewById(R.id.price);
            menuName.setText(bean.menuName);
            price.setText(bean.price + "元");
            menuItem.addView(itemView);
        }
    }
}
