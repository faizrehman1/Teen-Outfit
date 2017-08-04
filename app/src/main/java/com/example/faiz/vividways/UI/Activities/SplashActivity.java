package com.example.faiz.vividways.UI.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.faiz.vividways.Models.UserModel;
import com.example.faiz.vividways.Utils.AppLogs;
import com.example.faiz.vividways.R;
import com.example.faiz.vividways.Utils.FirebaseHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
//        Thread timerThread = new Thread(){
//            public void run(){
//                try{
//                    sleep(3000);
//                }catch(InterruptedException e){
//                    e.printStackTrace();
//                }finally{
//
//                    mAuthListener = new FirebaseAuth.AuthStateListener() {
//                        @Override
//                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//                            if (currentUser != null) {
//                                openMainScreen();
//
//                            }else{
//                                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
//                                overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
//                                startActivity(intent);
//                                finish();
//                            }
//                        }
//                    };
//                }
//            }
//        };
//        timerThread.start();


        Window window = SplashActivity.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(SplashActivity.this,R.color.colorPrimaryDark));
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                AppLogs.d("","Start splash screen");
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        final FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // User is signed in
                            FirebaseHandler.getInstance().getUsersRef().child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot!=null){
                                        if(dataSnapshot.getValue()!=null){
                                            UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                            UserModel.getInstance(

                                            userModel.getUser_email(),
                                                    userModel.getUser_password(),
                                                    userModel.getUser_userID(),
                                                    userModel.getUser_fname(),
                                                    userModel.getUser_lname(),
                                            userModel.getUser_imgURL(),
                                            userModel.getUser_country(),
                                            userModel.getUser_gender()
                                           );
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });




                            openMainScreen();
                        } else {
                            // User is signed out
                            Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                                overridePendingTransition(R.anim.slide_down,R.anim.slide_up);
                                startActivity(intent);
                                finish();
                        }
                    }
                };
                //add listener
                mAuth.addAuthStateListener(mAuthListener);
            }
        }, 3000);
    }






    private void openMainScreen() {
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
