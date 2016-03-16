package com.engine.privatefood.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.engine.privatefood.R;
import com.engine.privatefood.adpater.MenuAdapter;
import com.engine.privatefood.api.otoapi.LoadMenuApi;
import com.engine.privatefood.bean.MenuBean;
import com.engine.privatefood.bean.ShopBean;
import com.engine.privatefood.view.SelectItemView;
import com.engine.privatefood.view.SelectItemView.ISelectListener;
import com.squareup.otto.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
    ShopBean shop ;
    public static final String SHOP = "shop";
    @Bind(R.id.selectFoodLayout)
    LinearLayout selectFoodLayout;
    @Bind(R.id.rootView)
    RelativeLayout rootView;
    ArrayList<MenuBean> selectMenuList = new ArrayList<MenuBean>();
    String totalFormat = "共%d份%s元";
    String subtrack = "优惠%s元";
    String needValue = "差%s送";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        ButterKnife.bind(this);
        shop = getIntent().getParcelableExtra(SHOP);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getIntent().getStringExtra(shop.shopName));
        loadMenu();
    }

    private void loadMenu() {
        LoadMenuApi api = new LoadMenuApi();
        api.shopid = shop.shopid;
        api.execute();
    }

    @Subscribe
    public void onResponseMenu(LoadMenuApi api) {
        MenuAdapter adapter = new MenuAdapter(this, api.getListModel());
        adapter.setRootView(rootView, getSupportActionBar());
        listView.setAdapter(adapter);
        adapter.setListener(this);
    }


    @OnClick(R.id.cartLayout)
    public void showAllMenus(View view) {
        showPopwindow(selectFoodLayout);
    }


    @OnClick(R.id.generatorOrder)
    public void onSubmitClick(View view) {
        Intent intent = new Intent(this, GeneratorOrderActivity.class);
        intent.putParcelableArrayListExtra(GeneratorOrderActivity.MENUS, selectMenuList);
        intent.putExtra(GeneratorOrderActivity.SHOP,shop);
        startActivity(intent);
    }

    @Override
    public void onMenuChoice(MenuBean menuBean) {
        selectMenuList.add(menuBean);
        SelectItemView view = new SelectItemView(this, menuBean);
        view.setTag(menuBean);
        selectFoodLayout.addView(view);
        initNumber();
    }

    private void initNumber() {
        float totalPrice = 0f;
        for (MenuBean menuBean : selectMenuList) {
            totalPrice += menuBean.price;
        }
        if (totalPrice >= shop.maxAmount) {
            totalPrice = totalPrice - shop.subtrackPrice;
        }
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        number.setText(String.format(totalFormat, selectMenuList.size(), totalPrice == 0 ? "0" : decimalFormat.format(totalPrice)));
        if (totalPrice >= shop.limitAmount) {
            generatorOrder.setText("选好了");
            generatorOrder.setEnabled(true);
            subtrackPrice.setText(String.format(subtrack, decimalFormat.format(shop.subtrackPrice)));
        } else {
            generatorOrder.setText(String.format(needValue, decimalFormat.format(Math.abs(totalPrice - shop.limitAmount))));
            generatorOrder.setEnabled(false);
        }
    }


    private void showPopwindow(View locationView) {
        // 利用layoutInflater获得View
        if (selectMenuList.size() < 1) return;
        View contentView = LayoutInflater.from(this).inflate(R.layout.menu_pop_layout, null);
        final TextView numberView = (TextView) contentView.findViewById(R.id.number);
        final TextView subtrackView = (TextView) contentView.findViewById(R.id.subtrackPrice);
        numberView.setText(number.getText());
        subtrackView.setText(subtrackPrice.getText());
        final LinearLayout view = (LinearLayout) contentView.findViewById(R.id.itemLayout);
        view.setOrientation(LinearLayout.VERTICAL);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        for (MenuBean menuBean : selectMenuList) {
            final SelectItemView itemView = new SelectItemView(this, menuBean);
            itemView.setTag(menuBean);
            itemView.setListener(new ISelectListener() {
                @Override
                public void onMenuRemove(MenuBean bean) {
                    selectMenuList.remove(bean);
                    for (int i = 0; i < view.getChildCount(); i++) {
                        View v = view.getChildAt(i);
                        if (bean == v.getTag()) {
                            view.removeView(v);
                            initNumber();
                            numberView.setText(number.getText());
                            subtrackView.setText(subtrackPrice.getText());
                            break;
                        }
                    }
                }
            });
            view.addView(itemView);
        }
        final PopupWindow window = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        contentView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });

        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        ColorDrawable dw = new ColorDrawable(Color.WHITE);
        window.setBackgroundDrawable(dw);


        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(locationView,
                Gravity.BOTTOM, 0, 0);

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });

    }
}
