package com.example.faiz.vividways.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.R;

import java.util.ArrayList;

/**
 * Created by Faiz on 7/23/2017.
 */

public class ProfileGridAdapter extends BaseAdapter {

    public ArrayList<ItemObject> itemObjectArrayList;
    public Context mContext;
    public LayoutInflater layoutInflater;


    public ProfileGridAdapter(ArrayList<ItemObject> itemObjectArrayList, Context mContext) {
        this.itemObjectArrayList = itemObjectArrayList;
        this.mContext = mContext;
        this.layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

     //   View view = layoutInflater.inflate(R.layout.gridview_views,null);

     //   ImageView imageView = (ImageView)view.findViewById(R.id.profile_indi_image);
        ImageView imageView = new ImageView(mContext);
      //  imageView.setImageResource(mThumbIds[position]);
          imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    //    imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        imageView.setLayoutParams(new GridView.LayoutParams(200,200));
        Glide.with(mContext).load(itemObjectArrayList.get(position).getItemImageURl()).into(imageView);



        return imageView;
    }
}
