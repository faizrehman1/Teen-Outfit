package com.example.faiz.vividways.Adapters;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.faiz.vividways.AppLogs;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.R;
import com.example.faiz.vividways.UI.Home_Fragment;
import com.example.faiz.vividways.UI.List_Fragment;
import com.example.faiz.vividways.UI.MainActivity;
import com.example.faiz.vividways.UI.Statistic_Fragment;


import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<ItemObject> itemsList;
    private Context mContext;
    private ImageView imageView;
     public MainActivity mainActivity;
    public SectionListDataAdapter(Context context, ArrayList<ItemObject> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, final int i) {
          mainActivity = (MainActivity) mContext;
        ItemObject singleItem = itemsList.get(i);
//        holder.tvTitle.setBackgroundResource(R.mipmap.hanger_img);
        AppLogs.e("Image Ka URL",singleItem.getItemImageURl());

        holder.takeIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Take IT", Toast.LENGTH_SHORT).show();
                openListFragment(mContext,true);

            }
        });

        holder.leaveIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Leave IT", Toast.LENGTH_SHORT).show();
                openListFragment(mContext, false);
            }
        });

        holder.take_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Home_Fragment.getInstance().my_recycler_view.smoothScrollToPosition(i+1);

            }
        });

        holder.leave_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home_Fragment.getInstance().my_recycler_view.smoothScrollToPosition(i+1);
            }
        });

        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((MainActivity) mContext).getSupportFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.in_from_right,R.anim.out_from_left,R.anim.out_from_left,R.anim.in_from_right);
                mainActivity.Uploadbutton.setVisibility(View.GONE);
                mainActivity.appbar_TextView.setText("Statistic");
                MainActivity.back_image.setVisibility(View.VISIBLE);
                MainActivity.delete_image.setVisibility(View.VISIBLE);
                MainActivity.report_image.setVisibility(View.VISIBLE);
                ft.replace(R.id.fragment_container,new Statistic_Fragment());
                ft.addToBackStack("");
                ft.commit();
            }
        });


     //   Glide.with(mContext).load(singleItem.getItemImageURl()).into(holder.itemImage);


       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected CardView tvTitle;
        protected LinearLayout leaveIT,takeIT;
        protected ImageView itemImage;
        protected Button leave_btn,take_btn;

        public SingleItemRowHolder(View view) {
            super(view);


           this.leave_btn = (Button)view.findViewById(R.id.leave_btn);
            this.take_btn = (Button)view.findViewById(R.id.take_btn);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.leaveIT =(LinearLayout)view.findViewById(R.id.itemleave);
            this.takeIT =(LinearLayout)view.findViewById(R.id.itemtake);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


//                    Toast.makeText(v.getContext(),"Hello", Toast.LENGTH_SHORT).show();

                }
            });


        }

    }

    public void openListFragment(Context mContext, boolean b){
        Bundle data = new Bundle();
       if(b){
           //take it
           data.putBoolean("listb", b);
       }else{
          // leave it
           data.putBoolean("listb", b);
       }
       List_Fragment list_fragment = new List_Fragment();
        FragmentTransaction ft = ((MainActivity) mContext).getSupportFragmentManager()
                .beginTransaction().setCustomAnimations(R.anim.in_from_right,R.anim.out_from_left,R.anim.out_from_left,R.anim.in_from_right);;
        mainActivity.Uploadbutton.setVisibility(View.GONE);
        mainActivity.appbar_TextView.setText("List");
        mainActivity.back_image.setVisibility(View.VISIBLE);
        mainActivity.delete_image.setVisibility(View.GONE);
        mainActivity.report_image.setVisibility(View.GONE);
        mainActivity.menu_bar.setVisibility(View.GONE);
        list_fragment.setArguments(data);
        ft.replace(R.id.fragment_container,list_fragment);
        ft.addToBackStack("");
        ft.commit();
    }

}