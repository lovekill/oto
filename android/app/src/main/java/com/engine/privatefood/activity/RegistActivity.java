package com.engine.privatefood.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.engine.privatefood.R;
import com.engine.privatefood.api.otoapi.RegistApi;
import com.squareup.otto.Subscribe;

/**
 * Created by engine on 16/3/10.
 */
public class RegistActivity extends BaseActivity {
    @Bind(R.id.userName)
    EditText userName;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.password2)
    EditText password2;
    @Bind(R.id.normalUser)
    RadioButton normalUser;
    @Bind(R.id.businessUser)
    RadioButton businessUser;
    @Bind(R.id.userTypeGroup)
    RadioGroup userTypeGroup;

    private  int userType=1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_regist);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("注册");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        userTypeGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (i==R.id.normalUser){
                userType=1;
            }else {
                userType=2;
            }
        }
    };

    private boolean validateUserNameAndPassword(){
        String name = userName.getText().toString().trim();
        String p1=password.getText().toString();
        String p2 = password2.getText().toString();
        if (name.length()<0||name.length()>16){
            Toast.makeText(this,"用户名必须在0到16位",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!p1.equals(p2)){
            Toast.makeText(this,"密码不一致,请重新确认",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @OnClick(R.id.doRegist)
    public void doRegist(View view){
        if (validateUserNameAndPassword()){
            RegistApi registApi = new RegistApi();
            registApi.userName=userName.getText().toString().trim();
            registApi.password=password.getText().toString();
            registApi.userType=userType;
            registApi.execute();
        }
    }
    @Subscribe
    public void registResponse(RegistApi api){
        Toast.makeText(this,api.getModel().userName,Toast.LENGTH_SHORT).show();
    }
}
