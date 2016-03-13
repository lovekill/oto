package com.engine.privatefood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.engine.privatefood.R;
import com.engine.privatefood.UserManager;
import com.engine.privatefood.adpater.AddressAdapter;
import com.engine.privatefood.api.otoapi.LoadAdrressApi;
import com.engine.privatefood.bean.UserBean;
import com.squareup.otto.Subscribe;

/**
 * Created by engine on 16/3/13.
 */
public class AddressListActivty extends BaseActivity {
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.emptyView)
    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_address_list);
        ButterKnife.bind(this);
        listView.setEmptyView(emptyView);
        ActionBar actionBar= getSupportActionBar() ;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("我的地址");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        UserBean bean = UserManager.getInstance(this).getUser();
        LoadAdrressApi api = new LoadAdrressApi();
        api.userid = bean.userid;
        api.execute();
    }

    @Subscribe
    public void onAddressResponse(LoadAdrressApi api) {
        AddressAdapter addressAdapter = new AddressAdapter(this,api.getListModel());
        listView.setAdapter(addressAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.address,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_add_address){
            Intent intent=new Intent(this,EditAddressActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
