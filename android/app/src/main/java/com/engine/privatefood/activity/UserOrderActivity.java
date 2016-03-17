package com.engine.privatefood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.engine.privatefood.R;
import com.engine.privatefood.UserManager;
import com.engine.privatefood.adpater.UserOrderAdapter;
import com.engine.privatefood.api.otoapi.LoadUserOrderApi;
import com.engine.privatefood.bean.OrderBean;
import com.engine.privatefood.bean.OrderDetailBean;
import com.engine.privatefood.bean.UserBean;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Created by engine on 16/3/17.
 */
public class UserOrderActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.listView)
    ListView listView;
    List<OrderBean> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我的订单");
        actionBar.setDisplayHomeAsUpEnabled(true);
        loadOrderList();
    }

    public void loadOrderList(){
        LoadUserOrderApi api = new LoadUserOrderApi();
        UserBean userBean = UserManager.getInstance(this).getUser();
        if (userBean!=null){
            api.userid=userBean.userid;
            api.execute();
        }
    }

    @Subscribe
    public void getOrderResponse(LoadUserOrderApi api){
        list=api.getListModel();
        UserOrderAdapter adapter = new UserOrderAdapter(this,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        OrderBean bean = list.get(i);
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra(OrderDetailActivity.ORDER,bean);
        startActivity(intent);
    }
}
