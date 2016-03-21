package com.engine.privatefood.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.engine.privatefood.R;
import com.engine.privatefood.bean.Location;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by engine on 16/3/21.
 */
public class EditShopActivity extends BaseActivity implements AndroidImagePicker.OnPictureTakeCompleteListener,AndroidImagePicker.OnImagePickCompleteListener,AndroidImagePicker.OnImageCropCompleteListener {
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

    @Override
    public void onPictureTakeComplete(String picturePath) {

    }

    @Override
    public void onImagePickComplete(List<ImageItem> items) {

    }

    @Override
    public void onImageCropComplete(Bitmap bmp, float ratio) {
        shopImage.setImageBitmap(bmp);
        File file = new File(getCacheDir()+"/shop.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG,100,fos);
            Log.e(TAG,"filesize="+file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    @OnClick(R.id.shopImage)
    public void choiceShopImage(View view){
        int requestCode = REQ_IMAGE;

        Intent intent = new Intent(this,ImagesGridActivity.class);
        AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_SINGLE);
        AndroidImagePicker.getInstance().setShouldShowCamera(true);
        intent.putExtra("isCrop", true);
        requestCode = REQ_IMAGE_CROP;
        startActivityForResult(intent, requestCode);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == Activity.RESULT_OK){
            if (requestCode == REQ_IMAGE) {

                List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();
                for (ImageItem item:imageList){
                    Log.e(TAG,item.path);
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
        Location location = new Location(this);
        location.load();
        shopAddress.setText(location.address);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("增加店铺");
    }
    @Override
    protected void onDestroy() {
        AndroidImagePicker.getInstance().deleteOnImagePickCompleteListener(this);
        AndroidImagePicker.getInstance().removeOnImageCropCompleteListener(this);
        AndroidImagePicker.getInstance().deleteOnPictureTakeCompleteListener(this);
        super.onDestroy();
    }
}
