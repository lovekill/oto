package com.engine.privatefood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.engine.privatefood.R;
import com.engine.privatefood.UserManager;
import com.engine.privatefood.api.otoapi.GeneratorOrderApi;
import com.engine.privatefood.api.otoapi.GetDefaultAddressApi;
import com.engine.privatefood.bean.AddressBean;
import com.engine.privatefood.bean.MenuBean;
import com.engine.privatefood.bean.ShopBean;
import com.engine.privatefood.bean.UserBean;
import com.squareup.otto.Subscribe;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by engine on 16/3/15.
 */
public class GeneratorOrderActivity extends BaseActivity {
    public static final String MENUS = "menus";
    public static final String SHOP = "shop";
    List<MenuBean> list;
    @Bind(R.id.menuItem)
    LinearLayout menuItem;
    @Bind(R.id.totalPrice)
    TextView totalPrice;
    @Bind(R.id.realPrice)
    TextView realPrice;
    @Bind(R.id.subtrackPrice)
    TextView subtrackPrice;
    ShopBean shopBean;
    @Bind(R.id.shopName)
    TextView shopName;
    @Bind(R.id.addAddress)
    Button addAddress;
    @Bind(R.id.addressLayout)
    LinearLayout addressLayout;
    AddressBean bean;
    @Bind(R.id.submit)
    Button submit;

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
        getAddress();
    }

    public void initMenu() {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        float totalPrice = 0f;
        for (MenuBean menuBean : list) {
            View item = LayoutInflater.from(this).inflate(R.layout.menu_order_item, null);
            TextView menuName = (TextView) item.findViewById(R.id.menuName);
            TextView menuPrice = (TextView) item.findViewById(R.id.price);
            menuName.setText(menuBean.menuName);
            menuPrice.setText(menuBean.price + "元");
            menuItem.addView(item);
            totalPrice += menuBean.price;
        }
        shopName.setText(shopBean.shopName);
        this.totalPrice.setText(decimalFormat.format(totalPrice) + "元");
        if (shopBean.maxAmount != 0 && totalPrice >= shopBean.maxAmount) {
            this.realPrice.setText(decimalFormat.format(totalPrice - shopBean.maxAmount) + "元");
            this.subtrackPrice.setText(decimalFormat.format(shopBean.subtrackPrice) + "元");
        } else {
            this.realPrice.setText(decimalFormat.format(totalPrice) + "元");
            this.subtrackPrice.setText("0元");
        }
    }

    public void getAddress() {
        UserBean user = UserManager.getInstance(this).getUser();
        if (user != null) {
            GetDefaultAddressApi api = new GetDefaultAddressApi();
            api.userid = user.userid;
            api.execute();
        }
    }

    @Subscribe
    public void onAddressResponse(GetDefaultAddressApi api) {
        bean = api.getModel();
        View addrssView = LayoutInflater.from(this).inflate(R.layout.address_item, null);
        TextView name = (TextView) addrssView.findViewById(R.id.name);
        TextView phoneNumber = (TextView) addrssView.findViewById(R.id.phoneNumber);
        TextView address = (TextView) addrssView.findViewById(R.id.address);
        Button actionButton = (Button) addrssView.findViewById(R.id.statusButton);
        actionButton.setVisibility(View.GONE);
        name.setText(bean.name);
        phoneNumber.setText(bean.phoneNumber);
        address.setText(bean.address);
        addressLayout.removeAllViews();
        addressLayout.addView(addrssView);
    }

    @OnClick(R.id.addAddress)
    public void selectNewAddress(View view) {
        UserBean userBean = UserManager.getInstance(this).getUser();
        if (userBean != null) {
            Intent intent = new Intent(this, AddressListActivty.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.submit)
    public void submitOrder(View view) {
        UserBean userBean = UserManager.getInstance(this).getUser();
        GeneratorOrderApi api = new GeneratorOrderApi();
        api.shopid = shopBean.shopid;
        api.userid = userBean.userid;
        api.addressid = bean.addressid;
        StringBuilder sb = new StringBuilder();
        for (MenuBean menu : list) {
            sb.append(menu.menuId).append(",");
        }
        api.menus = sb.toString();
        api.execute();
    }

    @Subscribe
    public void onOrderResponse(GeneratorOrderApi api) {
        submit.setText("等待商家接单");
        submit.setBackgroundColor(getResources().getColor(R.color.green));
        submit.setEnabled(false);
    }
}
