package com.example.faiz.vividways.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.faiz.vividways.R;

public class MainActivity extends AppCompatActivity {

    public LinearLayout home_view,top_view,notification_view,profile_view,setting_view;
    public Fragment selectedFragment = null;
    public static Button Uploadbutton;
    public static TextView appbar_TextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        BottomNavigationView bottomNavigationView = (BottomNavigationView)
//                findViewById(R.id.bottom_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        appbar_TextView = (TextView) findViewById(R.id.main_appbar_textView);
        Uploadbutton = (Button) findViewById(R.id.upload_btn);

     //   Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
      //  window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
     //   window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        home_view = (LinearLayout)findViewById(R.id.home_view);

        top_view = (LinearLayout)findViewById(R.id.top_view);

        notification_view = (LinearLayout)findViewById(R.id.notification_view);

        profile_view = (LinearLayout)findViewById(R.id.profile_view);

        setting_view = (LinearLayout)findViewById(R.id.setting_view);


        home_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new Home_Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.commit();
            }
        });

        top_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new Top_Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.commit();
            }
        });

        notification_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new Notification_Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.commit();
            }
        });

        profile_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new Profile_Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.commit();
            }
        });

        setting_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = new Setting_Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new Home_Fragment());
        transaction.commit();


    }






}
