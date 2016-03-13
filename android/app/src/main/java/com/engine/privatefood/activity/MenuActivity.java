package com.engine.privatefood.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.engine.privatefood.R;
import com.engine.privatefood.adpater.MenuAdapter;
import com.engine.privatefood.api.otoapi.LoadMenuApi;
import com.engine.privatefood.bean.MenuBean;
import com.squareup.otto.Subscribe;

/**
 * Created by engine on 16/3/13.
 */
public class MenuActivity extends BaseActivity implements MenuAdapter.IChoiceMenu {
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.number)
    TextView number;
    @Bind(R.id.subtrackPrice)
    TextView subtrackPrice;
    @Bind(R.id.generatorOrder)
    Button generatorOrder;
    int shopid = -1;
    public static final String SHOPID="shopid";
    public static final String SHOPNAME = "shopName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        ButterKnife.bind(this);
        shopid = getIntent().getIntExtra(SHOPID,-1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getIntent().getStringExtra(SHOPNAME));
        loadMenu();
    }

    private void loadMenu(){
        LoadMenuApi api = new LoadMenuApi();
        api.shopid = shopid;
        api.execute();
    }

    @Subscribe
    public void onResponseMenu(LoadMenuApi api){
        MenuAdapter adapter = new MenuAdapter(this,api.getListModel());
        listView.setAdapter(adapter);
    }

    @Override
    public void onMenuChoice(MenuBean menuBean) {

    }
}
