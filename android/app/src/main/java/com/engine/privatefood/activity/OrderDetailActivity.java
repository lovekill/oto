package com.engine.privatefood.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.engine.privatefood.R;
import com.engine.privatefood.api.otoapi.LoadOrderDetailApi;
import com.engine.privatefood.bean.OrderBean;

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
    @Bind(R.id.realPrice)
    TextView realPrice;
    @Bind(R.id.subtrackPrice)
    TextView subtrackPrice;
    @Bind(R.id.submit)
    Button submit;

    public static final String ORDER="order";
    OrderBean orderBean ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        orderBean=getIntent().getParcelableExtra(ORDER);
        getOrderDetail();
    }

    public void getOrderDetail(){
        LoadOrderDetailApi api = new LoadOrderDetailApi();
        api.orderid = orderBean.orderid;
        api.execute();
    }
}
