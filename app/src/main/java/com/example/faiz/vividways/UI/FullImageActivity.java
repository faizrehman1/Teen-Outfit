package com.example.faiz.vividways.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.R;
import com.example.faiz.vividways.UI.Activities.SplashActivity;

import java.io.FileNotFoundException;

public class FullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image_view);
        ImageView imageView = (ImageView)findViewById(R.id.full_img);

        Window window = FullImageActivity.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(FullImageActivity.this, R.color.colorPrimaryDark));
        }
      //  SharedPreferences preferences = this.getSharedPreferences("img", Context.MODE_PRIVATE);

     //  Bitmap bmp =  preferences.getString("img", "");
   //     try {
     //       Bitmap bitmap = BitmapFactory.decodeStream(FullImageActivity.this
         //           .openFileInput("myImage"));
    //    } catch (FileNotFoundException e) {
      //      e.printStackTrace();
    //    }
        //byte[] bytes = getIntent().getByteArrayExtra("img");
       // Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        ItemObject itemObject = getIntent().getParcelableExtra("img");
        Glide.with(FullImageActivity.this).load(itemObject.getItemImageURl()).asBitmap().placeholder(R.mipmap.placeholder).into(imageView);
       // imageView.setImageBitmap(bmp);
    }
}
