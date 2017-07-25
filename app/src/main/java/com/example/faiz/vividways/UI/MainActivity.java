package com.example.faiz.vividways.UI;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.faiz.vividways.R;

public class MainActivity extends AppCompatActivity {

    public static MainActivity mainActivity;
    public LinearLayout home_view, top_view, notification_view, profile_view, setting_view, menu_bar;
    public Fragment selectedFragment = null;
    public static Button Uploadbutton;
    public static TextView appbar_TextView;
    public ImageView home_image, top_image, notification_image, setting_image, profile_image;
    public TextView home_text, top_text, notification_text, setting_text, profile_text;
    public static Context context;
    public static ImageView back_image, delete_image, report_image;

    public static MainActivity getInstance() {
        return mainActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;

//        BottomNavigationView bottomNavigationView = (BottomNavigationView)
//                findViewById(R.id.bottom_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        appbar_TextView = (TextView) findViewById(R.id.main_appbar_textView);
        Uploadbutton = (Button) findViewById(R.id.upload_btn);
        back_image = (ImageView) findViewById(R.id.back_image);
        report_image = (ImageView) findViewById(R.id.report_image);
        delete_image = (ImageView) findViewById(R.id.delete_image);
        //   Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        //  window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        //   window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     R.anim.in_from_right,R.anim.out_from_left,R.anim.out_from_left,R.anim.in_from_right
            //    overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                menu_bar.setVisibility(View.VISIBLE);
                getSupportFragmentManager().popBackStack();
            }
        });

        menu_bar = (LinearLayout) findViewById(R.id.menu_bar);
        home_view = (LinearLayout) findViewById(R.id.home_view);
        home_image = (ImageView) findViewById(R.id.home_image);
        home_text = (TextView) findViewById(R.id.home_text);
        top_view = (LinearLayout) findViewById(R.id.top_view);
        top_image = (ImageView) findViewById(R.id.top_image);
        top_text = (TextView) findViewById(R.id.top_text);
        notification_view = (LinearLayout) findViewById(R.id.notification_view);
        notification_image = (ImageView) findViewById(R.id.notification_image);
        notification_text = (TextView) findViewById(R.id.notification_text);
        profile_view = (LinearLayout) findViewById(R.id.profile_view);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        profile_text = (TextView) findViewById(R.id.profile_text);
        setting_view = (LinearLayout) findViewById(R.id.setting_view);
        setting_image = (ImageView) findViewById(R.id.setting_image);
        setting_text = (TextView) findViewById(R.id.setting_text);

        home_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new Home_Fragment();
                back_image.setVisibility(View.GONE);
                delete_image.setVisibility(View.GONE);
                report_image.setVisibility(View.GONE);
                home_image.setImageResource(R.mipmap.sel_home_icon);
                home_text.setTextColor(Color.parseColor("#da59a8"));
                top_image.setImageResource(R.mipmap.top5_icon);
                top_text.setTextColor(Color.parseColor("#bfbfbf"));
                notification_image.setImageResource(R.mipmap.notification_icon);
                notification_text.setTextColor(Color.parseColor("#bfbfbf"));
                profile_image.setImageResource(R.mipmap.profile_icon);
                profile_text.setTextColor(Color.parseColor("#bfbfbf"));
                setting_image.setImageResource(R.mipmap.settings_icon);
                setting_text.setTextColor(Color.parseColor("#bfbfbf"));


                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.commit();
            }
        });

        top_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_image.setImageResource(R.mipmap.home_icon);
                home_text.setTextColor(Color.parseColor("#bfbfbf"));
                top_image.setImageResource(R.mipmap.sel_top5_icon);
                top_text.setTextColor(Color.parseColor("#da59a8"));
                notification_image.setImageResource(R.mipmap.notification_icon);
                notification_text.setTextColor(Color.parseColor("#bfbfbf"));
                profile_image.setImageResource(R.mipmap.profile_icon);
                profile_text.setTextColor(Color.parseColor("#bfbfbf"));
                setting_image.setImageResource(R.mipmap.settings_icon);
                back_image.setVisibility(View.GONE);
                delete_image.setVisibility(View.GONE);
                report_image.setVisibility(View.GONE);
                setting_text.setTextColor(Color.parseColor("#bfbfbf"));
                selectedFragment = new Top_Fragment();
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.commit();
            }
        });

        notification_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFragment = new Notification_Fragment();
                home_image.setImageResource(R.mipmap.home_icon);
                home_text.setTextColor(Color.parseColor("#bfbfbf"));
                top_image.setImageResource(R.mipmap.top5_icon);
                top_text.setTextColor(Color.parseColor("#bfbfbf"));
                notification_image.setImageResource(R.mipmap.sel_notification_icon);
                notification_text.setTextColor(Color.parseColor("#da59a8"));
                profile_image.setImageResource(R.mipmap.profile_icon);
                profile_text.setTextColor(Color.parseColor("#bfbfbf"));
                back_image.setVisibility(View.GONE);
                delete_image.setVisibility(View.GONE);
                report_image.setVisibility(View.GONE);
                setting_image.setImageResource(R.mipmap.settings_icon);
                setting_text.setTextColor(Color.parseColor("#bfbfbf"));
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.commit();
            }
        });

        profile_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new Profile_Fragment();
                home_image.setImageResource(R.mipmap.home_icon);
                home_text.setTextColor(Color.parseColor("#bfbfbf"));
                top_image.setImageResource(R.mipmap.top5_icon);
                top_text.setTextColor(Color.parseColor("#bfbfbf"));
                notification_image.setImageResource(R.mipmap.notification_icon);
                notification_text.setTextColor(Color.parseColor("#bfbfbf"));
                profile_image.setImageResource(R.mipmap.sel_profile_icon);
                profile_text.setTextColor(Color.parseColor("#da59a8"));
                setting_image.setImageResource(R.mipmap.settings_icon);
                back_image.setVisibility(View.GONE);
                delete_image.setVisibility(View.GONE);
                report_image.setVisibility(View.GONE);
                setting_text.setTextColor(Color.parseColor("#bfbfbf"));
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.commit();
            }
        });

        setting_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new Setting_Fragment();
                home_image.setImageResource(R.mipmap.home_icon);
                home_text.setTextColor(Color.parseColor("#bfbfbf"));
                top_image.setImageResource(R.mipmap.top5_icon);
                top_text.setTextColor(Color.parseColor("#bfbfbf"));
                notification_image.setImageResource(R.mipmap.notification_icon);
                notification_text.setTextColor(Color.parseColor("#bfbfbf"));
                profile_image.setImageResource(R.mipmap.profile_icon);
                back_image.setVisibility(View.GONE);
                delete_image.setVisibility(View.GONE);
                report_image.setVisibility(View.GONE);
                profile_text.setTextColor(Color.parseColor("#bfbfbf"));
                setting_image.setImageResource(R.mipmap.sel_settings_icon);
                setting_text.setTextColor(Color.parseColor("#da59a8"));
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.commit();
            }
        });

//
//
//
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                        switch (item.getItemId()) {
//                            case R.id.action_home:
//                                break;
//                            case R.id.action_top:
//                                break;
//                            case R.id.action_notification:
//                                break;
//                            case R.id.action_profile:
//                                break;
//                            case R.id.action_setting:
//                                break;
//                        }
//
//
//                        return true;
//
//                    }
//                });


        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.fragment_container, new Home_Fragment());
        transaction.commit();


    }

    @Override
    public void onBackPressed() {

        menu_bar.setVisibility(View.VISIBLE);
        getSupportFragmentManager().popBackStack();

        // super.onBackPressed();

    }
}
