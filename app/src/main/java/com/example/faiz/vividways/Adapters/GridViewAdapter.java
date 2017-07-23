package com.example.faiz.vividways.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.R;

import java.util.ArrayList;

/**
 * Created by Faiz on 7/21/2017.
 */

public class GridViewAdapter extends BaseAdapter {

    public LayoutInflater inflater;
    public Context mContext;
    public ArrayList<ItemObject> itemObjectArrayList;

    public GridViewAdapter(Context mContext, ArrayList<ItemObject> itemObjectArrayList) {
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = mContext;
        this.itemObjectArrayList = itemObjectArrayList;
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

        View view = inflater.inflate(R.layout.top_grid_view,null);


        return null;
    }
}
