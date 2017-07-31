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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.faiz.vividways.Utils.AppLogs;
import com.example.faiz.vividways.Utils.FirebaseHandler;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.R;
import com.example.faiz.vividways.UI.Home_Fragment;
import com.example.faiz.vividways.UI.List_Fragment;
import com.example.faiz.vividways.UI.Activities.MainActivity;
import com.example.faiz.vividways.UI.Statistic_Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;


import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private static final String TAG = "SectionAdapter";
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
                openListFragment(mContext,true,itemsList,i);

            }
        });

        holder.leaveIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Leave IT", Toast.LENGTH_SHORT).show();
                openListFragment(mContext, false, itemsList,i);

            }
        });

        holder.take_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int takit_count=0;
         //       Home_Fragment.getInstance().my_recycler_view.smoothScrollToPosition(i+1);
                AppLogs.d(TAG,itemsList.get(i).getTakeit_count()+"");
                takit_count = itemsList.get(i).getTakeit_count()+1;
                itemsList.get(i).setTakeit_count(takit_count);
                final int finalTakit_count = takit_count;
                FirebaseHandler.getInstance().getPostRef()
                        .child(String.valueOf(itemsList.get(i).getItemID()))
                        .child("takeit_count")
                        .setValue(takit_count, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                              //  ItemObject itemObject = new ItemObject(itemsList.get(i).getItemID(),itemsList.get(i).getItemImageURl(),true,false);
                                FirebaseHandler.getInstance().getPostRef().child(String.valueOf(itemsList.get(i).getItemID())).child("take_it_check").setValue(true);
                                FirebaseHandler.getInstance().getUser_postRef().child(itemsList.get(i).getUserID()).child(itemsList.get(i).getItemID()).child("take_it_check").setValue(true);
                                FirebaseHandler.getInstance().getUser_postRef().child(itemsList.get(i).getUserID()).child(itemsList.get(i).getItemID()).child("takeit_count").setValue(finalTakit_count);

                                FirebaseHandler.getInstance().getUser_leaveit_post()
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                        .child("user-take-posts")
                                        .child(itemsList.get(i).getItemID())
                                        .setValue(new ItemObject(itemsList.get(i).getItemID(), itemsList.get(i).getItemImageURl(), true, false, itemsList.get(i).getUserID()), new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                itemsList.remove(i);
                                                notifyDataSetChanged();
                                            }
                                        });
                            }
                        });

            }
        });

        holder.leave_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int leave_it_count=0;
           //     Home_Fragment.getInstance().my_recycler_view.smoothScrollToPosition(i+1);
                AppLogs.d(TAG,itemsList.get(i).leaveit_count+"");
                Home_Fragment.getInstance().my_recycler_view.smoothScrollToPosition(i+1);
                AppLogs.d(TAG,itemsList.get(i).getLeaveit_count()+"");
                leave_it_count = itemsList.get(i).getLeaveit_count()+1;
                itemsList.get(i).setLeaveit_count(leave_it_count);
                final int finalLeave_it_count = leave_it_count;
                FirebaseHandler.getInstance().getPostRef()
                        .child(String.valueOf(itemsList.get(i).getItemID()))
                        .child("leaveit_count")
                        .setValue(leave_it_count, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                          //      ItemObject itemObject = new ItemObject(itemsList.get(i).getItemID(),itemsList.get(i).getItemImageURl(),true,false);
                                FirebaseHandler.getInstance().getPostRef().child(String.valueOf(itemsList.get(i).getItemID())).child("leave_it_check").setValue(true);
                                FirebaseHandler.getInstance().getUser_postRef().child(itemsList.get(i).getUserID()).child(itemsList.get(i).getItemID()).child("leave_it_check").setValue(true);
                                FirebaseHandler.getInstance().getUser_postRef().child(itemsList.get(i).getUserID()).child(itemsList.get(i).getItemID()).child("leaveit_count").setValue(finalLeave_it_count);

                                FirebaseHandler.getInstance().getUser_leaveit_post()
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                        .child("user-leave-posts")
                                        .child(itemsList.get(i).getItemID())
                                        .setValue(new ItemObject(itemsList.get(i).getItemID(), itemsList.get(i).getItemImageURl(), false, true, itemsList.get(i).getUserID()), new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                itemsList.remove(i);
                                                notifyDataSetChanged();
                                            }
                                        });
                            }
                        });


            }
        });

        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putParcelable("clickItem",itemsList.get(i));
                Statistic_Fragment statistic_fragment = new Statistic_Fragment();
                statistic_fragment.setArguments(bundle);
                FragmentTransaction ft = ((MainActivity) mContext).getSupportFragmentManager()
                        .beginTransaction();
                mainActivity.Uploadbutton.setVisibility(View.GONE);
                mainActivity.appbar_TextView.setText("Statistic");
                MainActivity.back_image.setVisibility(View.VISIBLE);
                MainActivity.delete_image.setVisibility(View.VISIBLE);
                MainActivity.report_image.setVisibility(View.VISIBLE);
                ft.replace(R.id.fragment_container,statistic_fragment);
                ft.addToBackStack("");
                ft.commit();
            }
        });


        Glide.with(mContext).load(singleItem.getItemImageURl()).placeholder(R.mipmap.placeholder).into(holder.itemImage);
        holder.take_num.setText(String.valueOf(singleItem.getTakeit_count()));
        holder.leave_num.setText(String.valueOf(singleItem.getLeaveit_count()));
        holder.caption.setText(singleItem.getCaption());
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
        private TextView leave_num,take_num,caption;

        public SingleItemRowHolder(View view) {
            super(view);

            this.leave_num = (TextView)view.findViewById(R.id.leave_no);
           this.leave_btn = (Button)view.findViewById(R.id.leave_btn);
            this.take_num = (TextView)view.findViewById(R.id.take_no);
            this.take_btn = (Button)view.findViewById(R.id.take_btn);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.leaveIT =(LinearLayout)view.findViewById(R.id.itemleave);
            this.takeIT =(LinearLayout)view.findViewById(R.id.itemtake);
            this.caption = (TextView)view.findViewById(R.id.caption_item);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


//                    Toast.makeText(v.getContext(),"Hello", Toast.LENGTH_SHORT).show();

                }
            });


        }

    }

    public void openListFragment(Context mContext, boolean b, ArrayList<ItemObject> itemsList, int i){
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
                .beginTransaction();
        mainActivity.Uploadbutton.setVisibility(View.GONE);
        mainActivity.appbar_TextView.setText("List");
        mainActivity.back_image.setVisibility(View.VISIBLE);
        mainActivity.delete_image.setVisibility(View.GONE);
        mainActivity.report_image.setVisibility(View.GONE);
        mainActivity.menu_bar.setVisibility(View.GONE);
      //  data.putParcelable("item", (Parcelable) itemsList);
        data.putParcelable("item",itemsList.get(i));
        list_fragment.setArguments(data);
        ft.replace(R.id.fragment_container,list_fragment);
        ft.addToBackStack("");
        ft.commit();
    }

}