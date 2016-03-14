package com.engine.privatefood.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.engine.privatefood.R;
import com.engine.privatefood.adpater.MenuAdapter;
import com.engine.privatefood.api.otoapi.LoadMenuApi;
import com.engine.privatefood.bean.MenuBean;
import com.engine.privatefood.view.SelectItemView;
import com.engine.privatefood.view.SelectItemView.ISelectListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by engine on 16/3/13.
 */
public class MenuActivity extends BaseActivity implements MenuAdapter.IChoiceMenu,ISelectListener {
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.number)
    TextView number;
    @Bind(R.id.subtrackPrice)
    TextView subtrackPrice;
    @Bind(R.id.generatorOrder)
    Button generatorOrder;
    int shopid = -1;
    public static final String SHOPID = "shopid";
    public static final String SHOPNAME = "shopName";
    @Bind(R.id.selectFoodLayout)
    LinearLayout selectFoodLayout;
    @Bind(R.id.rootView)
    RelativeLayout rootView ;
    List<MenuBean> selectMenuList = new ArrayList<MenuBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        ButterKnife.bind(this);
        shopid = getIntent().getIntExtra(SHOPID, -1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getIntent().getStringExtra(SHOPNAME));
        loadMenu();
    }

    private void loadMenu() {
        LoadMenuApi api = new LoadMenuApi();
        api.shopid = shopid;
        api.execute();
    }

    @Subscribe
    public void onResponseMenu(LoadMenuApi api) {
        MenuAdapter adapter = new MenuAdapter(this, api.getListModel());
        adapter.setRootView(rootView);
        listView.setAdapter(adapter);
        adapter.setListener(this);

    }


    @OnClick(R.id.cartLayout)
    public void showAllMenus(View view){
        selectFoodLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMenuChoice(MenuBean menuBean) {
        selectMenuList.add(menuBean);
        SelectItemView view = new SelectItemView(this,menuBean);
        view.setTag(menuBean);
        view.setListener(this);
        selectFoodLayout.addView(view);
    }

    @Override
    public void onMenuRemove(MenuBean bean) {
        selectMenuList.remove(bean);
        for (int i =0;i<selectFoodLayout.getChildCount();i++){
            View view = selectFoodLayout.getChildAt(i);
            if (bean==view.getTag()){
                selectFoodLayout.removeView(view);
                break;
            }
        }
    }

}
