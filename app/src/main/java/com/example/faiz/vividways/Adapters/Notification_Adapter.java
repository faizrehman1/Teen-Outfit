package com.example.faiz.vividways.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Faiz on 7/25/2017.
 */

public class Notification_Adapter extends BaseAdapter {

    public Context mContext;
    public ArrayList<ItemObject> itemObjectArrayList;
    public LayoutInflater inflater;

    public Notification_Adapter(Context mContext, ArrayList<ItemObject> itemObjectArrayList) {
        this.mContext = mContext;
        this.itemObjectArrayList = itemObjectArrayList;
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemObjectArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemObjectArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       ViewHolder  viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_notification_view,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.post_image_noti);
            viewHolder.circleImageView = (CircleImageView)convertView.findViewById(R.id.user_image_noti);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.user_post_noti);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        viewHolder.textView.setText(itemObjectArrayList.get(position).getItemID());
        Glide.with(mContext).load(itemObjectArrayList.get(position).itemImageURl).into(viewHolder.imageView);





        return convertView;
    }

    static class ViewHolder {
      public  ImageView imageView;
      public  CircleImageView circleImageView;
      public  TextView textView;

    }
}
