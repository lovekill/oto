package com.engine.privatefood.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.engine.privatefood.R;
import com.engine.privatefood.UserManager;
import com.engine.privatefood.api.otoapi.AddAddressApi;
import com.engine.privatefood.bean.Location;
import com.squareup.otto.Subscribe;

/**
 * Created by engine on 16/3/13.
 */
public class EditAddressActivity extends BaseActivity {
    @Bind(R.id.userName)
    EditText userName;
    @Bind(R.id.phoneNumber)
    EditText phoneNumber;
    @Bind(R.id.address)
    EditText address;
    @Bind(R.id.save)
    Button save;

    private  String name;
    private String number;
    private String adr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_address);
        ButterKnife.bind(this);
        ActionBar actionBar= getSupportActionBar() ;
        actionBar.setDisplayHomeAsUpEnabled(true);
        name= getIntent().getStringExtra("userName");
        adr=getIntent().getStringExtra("address");
        number= getIntent().getStringExtra("phoneNumber");
        if (name==null){
            actionBar.setTitle("新增地址");
            Location location =Location.getInstance(this).load();
            address.setText(location.address);
        }else {
            actionBar.setTitle("编辑地址");
        }
    }

    @OnClick(R.id.save)
    public void onSave(View view){
        if (validate()) {
            AddAddressApi api = new AddAddressApi();
            api.name=userName.getText().toString().trim();
            api.userid= UserManager.getInstance(this).getUser().userid;
            api.address=address.getText().toString().trim();
            api.phoneNumber=phoneNumber.getText().toString().trim();
            api.execute();
        }else {
            Toast.makeText(this,"所有信息都不能为空",Toast.LENGTH_SHORT).show();
        }
    }


    @Subscribe
    public void onSaveResponse(AddAddressApi api){
        Toast.makeText(this,"新增地址成功",Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean validate(){
        String name = userName.getText().toString().trim();
        String number = phoneNumber.getText().toString().trim();
        String adr=  address.getText().toString().trim();
        if (name.length()==0||number.length()==0||adr.length()==0){
            return false;
        }else {
            return true;
        }
    }
}
