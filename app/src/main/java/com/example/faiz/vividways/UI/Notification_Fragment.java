package com.example.faiz.vividways.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.faiz.vividways.Adapters.Notification_Adapter;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.R;
import com.example.faiz.vividways.UI.Activities.MainActivity;
import com.example.faiz.vividways.Utils.AppLogs;
import com.example.faiz.vividways.Utils.FirebaseHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Faiz on 7/20/2017.
 */

public class Notification_Fragment extends android.support.v4.app.Fragment {

    public View view;
    public ListView noti_listView;
    public ArrayList<ItemObject> itemObjectArrayList;
    public Notification_Adapter notification_adapter;
    Query query,query1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.notification_layout,null);
        noti_listView = (ListView)view.findViewById(R.id.notification_list);
        itemObjectArrayList = new ArrayList<>();

        MainActivity.appbar_TextView.setText("Notifications");
        MainActivity.Uploadbutton.setVisibility(View.GONE);





//        itemObjectArrayList.add(new ItemObject("your Outfit was chosen for the outfit of the World","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2F8519984.jpg?alt=media&token=01e871c0-7c8d-46ed-b1cb-b06b861aafe2"));
//        itemObjectArrayList.add(new ItemObject("your Outfit was chosen for the outfit of the Month","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fasa.jpg?alt=media&token=c4bd1517-4fe3-44bb-8a92-9af741dd52a7"));
//        itemObjectArrayList.add(new ItemObject("your Outfit was chosen...","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fasas.jpg?alt=media&token=e9032b88-4db7-4bf0-a157-a38a78488ed8"));
//        itemObjectArrayList.add(new ItemObject("your Outfit was chosen for the outfit of the day","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2FFH3_Tamo_Racemo_Vista.png?alt=media&token=74c5ecf8-b94d-444a-ae36-aa9fec9b94f2"));
//        itemObjectArrayList.add(new ItemObject("Your Outfit Choosen for top 5","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fsasa.jpg?alt=media&token=8a418970-9e67-443c-a812-24a9d29c8f12"));
//        itemObjectArrayList.add(new ItemObject("your Outfit was chosen for the outfit of the day","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fsuper-cars.png?alt=media&token=03d3c9c5-11a7-4941-9184-0ca691b5fe94"));
//        itemObjectArrayList.add(new ItemObject("your Outfit was chosen for the outfit of the week","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fmaxresdefault.jpg?alt=media&token=12eacaf1-0e48-49ea-8fb7-3789e6f9e712"));
//        itemObjectArrayList.add(new ItemObject("your Outfit was chosen for the outfit of your country","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2FFH3_Tamo_Racemo_Vista.png?alt=media&token=74c5ecf8-b94d-444a-ae36-aa9fec9b94f2"));
//        itemObjectArrayList.add(new ItemObject("your Outfit was chosen for the outfit of the week","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fasas.jpg?alt=media&token=e9032b88-4db7-4bf0-a157-a38a78488ed8"));
//        itemObjectArrayList.add(new ItemObject("your Outfit was chosen for the outfit of the day","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fas.jpg?alt=media&token=d6a071d9-840e-4980-93e7-917100312614"));
//        itemObjectArrayList.add(new ItemObject("Your Outfit Choosen for top 5","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fasa.jpg?alt=media&token=c4bd1517-4fe3-44bb-8a92-9af741dd52a7"));
//        itemObjectArrayList.add(new ItemObject("your Outfit was chosen for the outfit of the day","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2F8519984.jpg?alt=media&token=01e871c0-7c8d-46ed-b1cb-b06b861aafe2"));
//
//


//            query = FirebaseHandler.getInstance().getPostRef().orderByChild("timestamp").startAt(trim(new Date(System.currentTimeMillis()-86400000))).endAt(trim(new Date(System.currentTimeMillis())));
//
//            query.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if(dataSnapshot!=null){
//                        if(dataSnapshot.getValue()!=null){
//                            AppLogs.d("TAG_SNAP",dataSnapshot.getValue().toString());
//                        }
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.MONTH, -1);
//        Date result = cal.getTime();
        long daysInpast= new Date().getTime() - TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS);
        query1 = FirebaseHandler.getInstance().getPostRef().orderByChild("takeit_count").startAt(1).endAt(100).limitToLast(3);
 //.orderByChild("timestamp").startAt(trim(new Date(daysInpast))).endAt(trim(new Date(System.currentTimeMillis())))
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if(dataSnapshot.getValue()!=null){
                        AppLogs.d("TAG_SNAP",dataSnapshot.getValue().toString());
                        for(DataSnapshot data:dataSnapshot.getChildren()){

                            ItemObject itemObject = data.getValue(ItemObject.class);
                            Date date = new Date(itemObject.getTimestamp());
                            Date currDate = new Date(System.currentTimeMillis());
                            long diff = date.getTime() - currDate.getTime();
                        String timeDiff = "Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                            String newString = timeDiff.replace("-","");
                            String s = "";
                            if(newString.equals("7")){
                                s = "Your Outfit was choosen for the outfit of the Week";
                            }else if(newString.equals("30")){
                                s = "Your Outfit was choosen for the outfit of the Month";
                            }else if(newString.equals("1")){
                                s = "Your Outfit was choosen for the outfit of the Day";
                            }else{
                                s= "Your Outfit wa choosen..";
                            }

                                if(!itemObject.getUserID().equals(FirebaseAuth.getInstance().getCurrentUser())) {
                                    itemObjectArrayList.add(new ItemObject(s, itemObject.getItemImageURl()));

                                    notification_adapter.notifyDataSetChanged();
                                }
                        }

                        if(itemObjectArrayList.size()>0){
                            checkPost(itemObjectArrayList);
                        }


                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        notification_adapter = new Notification_Adapter(getActivity(),itemObjectArrayList);
        noti_listView.setAdapter(notification_adapter);


//        FirebaseHandler.getInstance().getPostRef().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot!=null){
//                    if(dataSnapshot.getValue()!=null) {
//                        for (DataSnapshot data : dataSnapshot.getChildren()) {
//                            ItemObject itemObject = data.getValue(ItemObject.class);
//                            itemObjectArrayList.add(new ItemObject(itemObject.getItemID(),itemObject.getItemImageURl()));
//                    //        notification_adapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//
//                if(itemObjectArrayList.size()>0){
//                    checkPost(itemObjectArrayList);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });






        return view;
    }

    private void checkPost(ArrayList<ItemObject> itemObjectArrayList) {
        if(itemObjectArrayList.size()>0){
            for (int i = 0; i < itemObjectArrayList.size(); i++) {

            }
        }
    }

    public static long trim(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        return calendar.getTime().getTime();
    }



}
