package com.example.faiz.vividways.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.faiz.vividways.Adapters.UserList_Adapter;
import com.example.faiz.vividways.Models.UserModel;
import com.example.faiz.vividways.R;

import java.util.ArrayList;

/**
 * Created by Faiz on 7/23/2017.
 */

public class Leave_Fragment extends android.support.v4.app.Fragment {

    public View view;
    public ListView listView;
    public ArrayList<UserModel> userModelArrayList;
    public UserList_Adapter userList_adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.leave_frag,null);
        userModelArrayList = new ArrayList<>();

        listView = (ListView)view.findViewById(R.id.leave_listview);

        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));
        userModelArrayList.add(new UserModel("Faiz","Rehman","https://firebasestorage.googleapis.com/v0/b/luminous-torch-4640.appspot.com/o/defaults%2Fuser.png?alt=media&token=5430f4a2-d3f0-4cd1-9149-49938181dcb2"));

        userList_adapter = new UserList_Adapter(userModelArrayList,getActivity());
            listView.setDivider(null);
           listView.setAdapter(userList_adapter);





        return view;
    }
}
