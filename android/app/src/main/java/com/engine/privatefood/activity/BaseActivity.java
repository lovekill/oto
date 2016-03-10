package com.engine.privatefood.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.engine.privatefood.BusProvider;
import com.engine.privatefood.api.FailResponse;
import com.engine.privatefood.fragment.BaseFragment;
import com.squareup.otto.Subscribe;
/**
 * Created by engine on 15/7/27.
 */
public class BaseActivity extends AppCompatActivity{
    public String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getBus().register(this);
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Subscribe
    public void netError(FailResponse model){
        Log.e(TAG, "net Error response") ;
        Toast.makeText(this,"服务器异常",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getBus().unregister(this);
    }


    public void addFragment(BaseFragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(android.R.id.content,fragment,fragment.getClass().getSimpleName());
                ft.addToBackStack("tag");
        ft.commit();
    }

    public void addFragment(BaseFragment fragment,int contentId){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(contentId,fragment,fragment.getClass().getSimpleName());
        ft.addToBackStack("tag");
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    public void appBack(){
        FragmentManager fragmentManager = getSupportFragmentManager() ;
        if (fragmentManager.getBackStackEntryCount()>1){
            fragmentManager.popBackStack();
        }else {
            finish();
        }
    }

    public void appBackFragment(){
           FragmentManager fragmentManager = getSupportFragmentManager() ;
           fragmentManager.popBackStack();
    }
    @Override
    public void onBackPressed() {
        appBack();
    }
}
