package com.example.faiz.vividways.UI;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.faiz.vividways.Adapters.UserList_Adapter;
import com.example.faiz.vividways.AppLogs;
import com.example.faiz.vividways.FirebaseHandler;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.Models.UserModel;
import com.example.faiz.vividways.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Faiz on 7/23/2017.
 */

public class Leave_Fragment extends android.support.v4.app.Fragment {

    public View view;
    public ListView listView;
    public ArrayList<UserModel> userModelArrayList;
    public UserList_Adapter userList_adapter;
    private static final String ARG_PARAM1 = "param1";
    public static ItemObject itemObject;

    public static Leave_Fragment newInstance(ItemObject memberData) {
        AppLogs.d("kkk", "admin get argument");
        Leave_Fragment f = new Leave_Fragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,memberData);
        f.setArguments(args);

        return f;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.leave_frag,null);
        userModelArrayList = new ArrayList<>();

        listView = (ListView)view.findViewById(R.id.leave_listview);
        userList_adapter = new UserList_Adapter(userModelArrayList,getActivity());
        listView.setDivider(null);
        listView.setAdapter(userList_adapter);





            AppLogs.d("Leave_TAG",itemObject+"");




//        FirebaseHandler.getInstance().getUser_leaveit_post()
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                            if(dataSnapshot!=null){
//                                if(dataSnapshot.getValue()!=null){
//                                    for(DataSnapshot data:dataSnapshot.getChildren()){
//                                        for(DataSnapshot dataChild:data.getChildren()){
//                                            AppLogs.d("Node_Data",dataChild.getValue().toString());
//                                            ItemObject itemObject = dataChild.getValue(ItemObject.class);
                                            if(itemObject.isLeave_it_check() && !itemObject.isTake_it_check()){

                                                FirebaseHandler.getInstance().getUsersRef()
                                                        .child(itemObject.getUserID())
                                                        .addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                               if(dataSnapshot!=null){
                                                                   if(dataSnapshot.getValue()!=null){
                                                                       UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                                                       userModelArrayList.add(userModel);
                                                                       userList_adapter.notifyDataSetChanged();
                                                                   }
                                                               }
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });


//
                                           }
//                                        }
//                                    }
//                                }
//                            }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//
//
//
//





        return view;
    }
}
