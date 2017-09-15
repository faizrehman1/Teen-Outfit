package com.example.faiz.vividways.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.faiz.vividways.Models.UserModel;
import com.example.faiz.vividways.R;
import com.example.faiz.vividways.Utils.AppLogs;
import com.example.faiz.vividways.Utils.FirebaseHandler;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

/**
 * Created by AST on 9/7/2017.
 */

public class NotificationServices extends Service {
    String userId;
    long timeSTamp;
    private DatabaseReference firebaseRef;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service OnCreate", Toast.LENGTH_SHORT).show();
        firebaseRef = FirebaseDatabase.getInstance().getReference();

        if (UserModel.getInstanceIfNotNull() != null) {
            NotificationSharedPref.setCurrentUser(NotificationServices.this, UserModel.getInstanceIfNotNull());
            final UserModel user = UserModel.getInstanceIfNotNull();
            if (user != null) {
                userId = user.getUser_userID();
                if (userId != null) {
                    checkForTimeStamp();
                } else {
                    Log.d("TAG", "userId is null");
                }
            }
        } else {
            Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show();
            final UserModel user = NotificationSharedPref.getCurrentUser(NotificationServices.this);
            if (user != null) {
                userId = user.getUser_userID();
                if (userId != null) {
                    checkForTimeStamp();
                } else {
                    Log.d("TAG", "userId is null");
                }
            }

        }
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        Toast.makeText(this,"Service Started",Toast.LENGTH_SHORT).show();
        return START_STICKY;

    }

    public void checkForTimeStamp() {
        FirebaseHandler.getInstance().getActivitiesSeenByUser().child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                timeSTamp = Long.parseLong(dataSnapshot.getValue().toString());
                checkForGroups(timeSTamp);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkForGroups(long timeSTamp) {
        FirebaseHandler.getInstance().getPostRef().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                if(dataSnapshot!=null){
//                    if(dataSnapshot.getValue()!=null){
//                        AppLogs.d("TAG",dataSnapshot.getValue().toString());
//                        Toast.makeText(NotificationServices.this,"Retrieve Data",Toast.LENGTH_SHORT).show();
//
//                    }
//                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot!=null){
                    if(dataSnapshot.getValue()!=null){
                        AppLogs.d("TAG",dataSnapshot.getValue().toString());
                        Toast.makeText(NotificationServices.this,"Retrieve Data",Toast.LENGTH_SHORT).show();
                            sendNotification();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void sendNotification() {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("VividWays")
                .setContentText("Outfit" + " : " + "LIke")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Random random = new Random();
        int randomNumber = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(randomNumber, notificationBuilder.build());
    }

}
