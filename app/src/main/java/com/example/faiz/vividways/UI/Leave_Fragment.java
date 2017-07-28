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
    public ArrayList<String> userList;


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
        userList = new ArrayList<>();
        listView = (ListView)view.findViewById(R.id.leave_listview);
        userList_adapter = new UserList_Adapter(userModelArrayList,getActivity());
        listView.setDivider(null);
        listView.setAdapter(userList_adapter);





            AppLogs.d("Leave_TAG",itemObject+"");

            FirebaseHandler.getInstance().getUser_leaveit_post()
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot!=null){
                                if(dataSnapshot.getValue()!=null){
                                    AppLogs.d("TAG_snap",dataSnapshot.getValue().toString());
                                    for(DataSnapshot data:dataSnapshot.getChildren()){
                                        AppLogs.d("TAG_snap",data.getValue().toString());
                                        for(DataSnapshot again_data:data.getChildren()) {
                                            if (again_data.getKey().equals("user-leave-posts")){
                                                AppLogs.d("TAG_snap", data.getValue().toString());
                                            for (DataSnapshot data_snap : again_data.getChildren()) {
                                                ItemObject Object_item = data_snap.getValue(ItemObject.class);
                                                AppLogs.d("TAG_snap", Object_item.toString());
                                                if (itemObject.getItemID().equals(Object_item.getItemID())) {
                                                    AppLogs.d("TAG_snap", data.getKey().toString());
                                                    userList.add(data.getKey().toString());
                                                }
                                            }
                                        }
                                        }
                                    }
                                //    AppLogs.d("TAG_snap",dataSnapshot.getValue().toString());
                                }
                            }
                            getUser();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });







        return view;
    }

    private void getUser() {
        if(userList.size()>0){
            for (String s : userList) {
                FirebaseHandler.getInstance()
                        .getUsersRef()
                        .child(s)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot!=null){
                                        if(dataSnapshot.getValue()!=null){
                                            AppLogs.d("TAG_snap",dataSnapshot.getValue().toString());
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
            }
        }
    }
}
