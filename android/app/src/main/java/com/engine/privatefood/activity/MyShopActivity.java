package com.engine.privatefood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.engine.privatefood.R;

/**
 * Created by engine on 16/3/21.
 */
public class MyShopActivity extends BaseActivity {
    @Bind(R.id.emptyView)
    TextView emptyView;
    @Bind(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        ButterKnife.bind(this);
        listView.setEmptyView(emptyView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我的店铺");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_shop,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_add_shop){
            Intent intent=new Intent(this,EditShopActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
