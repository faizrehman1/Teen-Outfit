package com.example.faiz.vividways.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.faiz.vividways.Models.UserModel;
import com.example.faiz.vividways.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Faiz on 7/23/2017.
 */

public class UserList_Adapter extends BaseAdapter {

    public ArrayList<UserModel> userModelArrayList;
   public Context mContext;
    public LayoutInflater layoutInflater;

    public UserList_Adapter(ArrayList<UserModel> userModelArrayList, Context mContext) {
        this.userModelArrayList = userModelArrayList;
        this.mContext = mContext;
        this.layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return userModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.list_user_view,null);
        TextView textView = (TextView)view.findViewById(R.id.user_name_list);
        CircleImageView circleImageView = (CircleImageView)view.findViewById(R.id.user_image_list);

        UserModel userModel = userModelArrayList.get(position);

        textView.setText(userModel.getUser_fname()+" "+userModel.getUser_lname());
        Glide.with(mContext).load(userModel.getUser_imgURL()).into(circleImageView);



        return view;
    }
}
