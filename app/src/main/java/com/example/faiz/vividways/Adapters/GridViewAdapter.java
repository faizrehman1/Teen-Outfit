package com.example.faiz.vividways.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.Models.TopItems;
import com.example.faiz.vividways.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Faiz on 7/21/2017.
 */

public class GridViewAdapter extends ArrayAdapter<TopItems> {

    public LayoutInflater inflater;
    public Context mContext;
    public ArrayList<TopItems> itemObjectArrayList;
    public ArrayList<TopItems> backUpList;
    private int mCurrentFilterLength;

    public GridViewAdapter(Context mContext, ArrayList<TopItems> itemObjectArrayList) {
        super(mContext,R.layout.top_grid_view);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = mContext;
        this.itemObjectArrayList = itemObjectArrayList;
        this.backUpList = new ArrayList<>(itemObjectArrayList);
    }

    @Override
    public int getCount() {
        return itemObjectArrayList.size();
    }

    @Nullable
    @Override
    public TopItems getItem(int position) {
      //  return super.getItem(position);
       return itemObjectArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.top_grid_view,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imgStatus= (ImageView) convertView.findViewById(R.id.top_image_card);
            viewHolder.take_no = (TextView)convertView.findViewById(R.id.tak_no);
            viewHolder.Leave_no = (TextView)convertView.findViewById(R.id.leave_no);
            viewHolder.circleImageView = (CircleImageView)convertView.findViewById(R.id.user_card_image);
            viewHolder.user_nametext = (TextView)convertView.findViewById(R.id.user_card_name);
            viewHolder.user_countrytext = (TextView)convertView.findViewById(R.id.user_card_country);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Glide.with(mContext).load(itemObjectArrayList.get(position).getItemObject().getItemImageURl()).into(viewHolder.imgStatus);
        viewHolder.Leave_no.setText(itemObjectArrayList.get(position).getItemObject().getLeaveit_count()+"");
        viewHolder.take_no.setText(itemObjectArrayList.get(position).getItemObject().getTakeit_count()+"");
        Glide.with(mContext).load(itemObjectArrayList.get(position).getUserModel().getUser_imgURL()).into(viewHolder.circleImageView);
        viewHolder.user_nametext.setText(itemObjectArrayList.get(position).getUserModel().getUser_fname()+""+itemObjectArrayList.get(position).getUserModel().getUser_lname());
        viewHolder.user_countrytext.setText(itemObjectArrayList.get(position).getUserModel().getUser_country());


        return convertView;
    }

    @Override
    public void add(TopItems object) {
        super.add(object);
        backUpList.add(object);
      //  notifyDataSetChanged();
    }



    public void filterMembers(ArrayList<String> days, String country, String selectOption) {
//        when start filtering

        int i = 0;

        if (selectOption.equals("today")) {
            itemObjectArrayList.clear();
            itemObjectArrayList.addAll(backUpList);
            while (i < itemObjectArrayList.size()) {

                Date date = new Date(itemObjectArrayList.get(i).getItemObject().getTimestamp());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
                String day = sdf.format(date);
                //  for (String s : days) {
                if (days.contains(day)) {
                    itemObjectArrayList.remove(i);
                    //     }
                    //   }
                    // if (!day.equals(days[i])) {

                    //  } else {
                } else {
                    i++;
                }
                // }
            }
            notifyDataSetChanged();
        }else if(selectOption.equals("Week") || selectOption.equals("Half Of Month")){
            itemObjectArrayList.clear();
            itemObjectArrayList.addAll(backUpList);
            while (i < itemObjectArrayList.size()) {
                Date date = new Date(itemObjectArrayList.get(i).getItemObject().getTimestamp());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
                String day = sdf.format(date);

              //  for (int i1 = days.length-1; i1 >=0; i1--) {
                for (String s : days) {
                    if(!s.equals(day)){
                        itemObjectArrayList.remove(i);
                          break;
                        //    }
                    }
                }

                i++;
                //for (String s : days) {

              //  }

            }
            notifyDataSetChanged();
        }
            //   int filterLength = days.length;
            //   if (filterLength == 0 || filterLength < mCurrentFilterLength) {
//      //      mCurrentFilterLength = filterLength;
//        if(itemObjectArrayList.size()==0) {
//            itemObjectArrayList.clear();
//            itemObjectArrayList.addAll(backUpList);
//        notifyDataSetChanged();
//        }
//            if (filterLength == 0) {
//                notifyDataSetChanged();
//                return;
//            }
            //  }
//
//        int i = 0;
//        while (i < itemObjectArrayList.size()) {
//
//            Date date = new Date(itemObjectArrayList.get(i).getItemObject().getTimestamp());
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
//         String  day = sdf.format(date);
//          //  for (String s : days) {
//                if(!s.equals(day)){
//                    itemObjectArrayList.remove(i);
//           //     }
//         //   }
//           // if (!day.equals(days[i])) {
//
//          //  } else {
//                i++;
//            }
//       // }
//    //    mCurrentFilterLength = filterLength;
//        notifyDataSetChanged();

    }




    static class ViewHolder {
        ImageView imgStatus;
        TextView take_no,Leave_no;
        CircleImageView circleImageView;
        TextView user_nametext,user_countrytext;
    }
}
