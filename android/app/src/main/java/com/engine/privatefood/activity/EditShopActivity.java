package com.engine.privatefood.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.engine.privatefood.R;
import com.engine.privatefood.api.otoapi.AddShopApi;
import com.engine.privatefood.bean.Location;
import com.engine.privatefood.bean.UserBean;
import com.engine.privatefood.util.EditUtils;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by engine on 16/3/21.
 */
public class EditShopActivity extends BaseActivity implements AndroidImagePicker.OnPictureTakeCompleteListener, AndroidImagePicker.OnImagePickCompleteListener, AndroidImagePicker.OnImageCropCompleteListener {
    @Bind(R.id.shopImage)
    ImageView shopImage;
    @Bind(R.id.shopName)
    EditText shopName;
    @Bind(R.id.userName)
    EditText userName;
    @Bind(R.id.phoneNumber)
    EditText phoneNumber;
    @Bind(R.id.shopAddress)
    EditText shopAddress;
    @Bind(R.id.submit)
    Button submit;
    private final int REQ_IMAGE = 1433;
    private final int REQ_IMAGE_CROP = 1435;
    @Bind(R.id.limitAmount)
    EditText limitAmount;
    @Bind(R.id.maxAmount)
    EditText maxAmount;
    @Bind(R.id.subtrackPrice)
    EditText subtrackPrice;
    private File uplodFile ;
    @Override
    public void onPictureTakeComplete(String picturePath) {

    }

    @Override
    public void onImagePickComplete(List<ImageItem> items) {

    }

    @Override
    public void onImageCropComplete(Bitmap bmp, float ratio) {
        shopImage.setImageBitmap(bmp);
        uplodFile= new File(getCacheDir() + "/shop.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(uplodFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.shopImage)
    public void choiceShopImage(View view) {
        int requestCode = REQ_IMAGE;

        Intent intent = new Intent(this, ImagesGridActivity.class);
        AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_SINGLE);
        AndroidImagePicker.getInstance().setShouldShowCamera(true);
        intent.putExtra("isCrop", true);
        requestCode = REQ_IMAGE_CROP;
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_IMAGE) {

                List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();
                for (ImageItem item : imageList) {
                    Log.e(TAG, item.path);
                }
            }/*else if(requestCode == REQ_IMAGE_CROP){
                Bitmap bmp = (Bitmap)data.getExtras().get("bitmap");
                Log.i(TAG,"-----"+bmp.getRowBytes());
            }*/
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        ButterKnife.bind(this);
        AndroidImagePicker.getInstance().addOnImageCropCompleteListener(this);
        AndroidImagePicker.getInstance().addOnImageCropCompleteListener(this);
        AndroidImagePicker.getInstance().setOnImagePickCompleteListener(this);
        Location location =Location.getInstance(this).load();
        shopAddress.setText(location.address);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("增加店铺");
    }

    @OnClick(R.id.submit)
    public void submit(View view){
        Location location = Location.getInstance(this).load();
        if (uplodFile==null){
            Toast.makeText(this,"请为你的店铺拍摄一张照片",Toast.LENGTH_SHORT).show();
            return;
        }
        if (validate()&&validatePrice()){
            AddShopApi addShopApi = new AddShopApi();
            addShopApi.address=shopAddress.getText().toString().trim();
            addShopApi.shopName=shopName.getText().toString().trim();
            addShopApi.userid=userBean.userid;
            addShopApi.latitude=location.latitude;
            addShopApi.lontitud=location.lontitude;
            addShopApi.limitAmount=Float.parseFloat(limitAmount.getText().toString());
            addShopApi.maxAmount=Float.parseFloat(maxAmount.getText().toString());
            addShopApi.subtrackPrice=Float.parseFloat(subtrackPrice.getText().toString());
            addShopApi.addFile(uplodFile);
            addShopApi.execute();
        }else {
            Toast.makeText(this,"输入参数不合法",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validatePrice(){
        return  EditUtils.isFloatValue(limitAmount,maxAmount,subtrackPrice);
    }
    public boolean validate(){
        return !EditUtils.editsIsNull(true,shopName,shopAddress,userName,limitAmount,maxAmount,subtrackPrice);
    }

    @Override
    protected void onDestroy() {
        AndroidImagePicker.getInstance().deleteOnImagePickCompleteListener(this);
        AndroidImagePicker.getInstance().removeOnImageCropCompleteListener(this);
        AndroidImagePicker.getInstance().deleteOnPictureTakeCompleteListener(this);
        super.onDestroy();
    }
}
