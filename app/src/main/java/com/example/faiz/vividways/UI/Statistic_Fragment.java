package com.example.faiz.vividways.UI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.faiz.vividways.Adapters.CustomPieChart;
import com.example.faiz.vividways.AppController;
import com.example.faiz.vividways.AppLogs;
import com.example.faiz.vividways.FirebaseHandler;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.Models.UserModel;
import com.example.faiz.vividways.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Faiz on 7/21/2017.
 */

public class Statistic_Fragment extends android.support.v4.app.Fragment {

    public ImageView imageView_item;
    private static final String TAG = "Statistic_Fragment";
    private TextView leave_count, take_count;
    public LinearLayout pieChart_take, pieChart_leave;
    CustomPieChart pieChart;
    LinearLayout linearLayout;
    private ArrayList<String> stringArrayList;

    private ArrayList<String> stringArrayList_take;
    private ArrayList<String> stringArrayList_leave;

    private ItemObject itemObject;

     MainActivity parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.statistic_layout, null);
        imageView_item = (ImageView) view.findViewById(R.id.select_item);
        leave_count = (TextView) view.findViewById(R.id.select_item_leave);
        take_count = (TextView) view.findViewById(R.id.select_item_take);
        pieChart_take = (LinearLayout) view.findViewById(R.id.pie_take_it);
        pieChart_leave = (LinearLayout) view.findViewById(R.id.pie_leave_it);
        stringArrayList_take = new ArrayList<>();
        stringArrayList_leave = new ArrayList<>();
        stringArrayList = new ArrayList<>();

        if (getArguments() != null) {
            itemObject = getArguments().getParcelable("clickItem");
            AppLogs.d(TAG, itemObject.getItemID() + "");
            Glide.with(getActivity()).load(itemObject.getItemImageURl()).into(imageView_item);


            FirebaseHandler.getInstance()
                    .getPostRef()
                    .child(itemObject.getItemID())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot!=null){
                                if(dataSnapshot.getValue()!=null){
                                 itemObject = dataSnapshot.getValue(ItemObject.class);
                                    leave_count.setText(String.valueOf(itemObject.getLeaveit_count()));
                                    take_count.setText(String.valueOf(itemObject.getTakeit_count()));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




            FirebaseHandler.getInstance().getUser_leaveit_post()
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot != null) {
                                if (dataSnapshot.getValue() != null) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        //   for(DataSnapshot data_again:data.getChildren()){
                                        AppLogs.d(TAG, "" + data.getKey());
                                        AppLogs.d(TAG, "" + data.getValue().toString());
                                        for(DataSnapshot dataSnap:data.getChildren()) {
                                            if (dataSnap.getKey().equals("user-leave-posts")) {
                                           //     for (DataSnapshot data_snap : dataSnap.getChildren()) {
                                                    if (dataSnap.hasChild(itemObject.getItemID())) {
                                                        stringArrayList_leave.add(data.getKey());
                                                    }
                                           //     }
                                            } else if (dataSnap.getKey().equals("user-take-posts")) {
                                            //    for (DataSnapshot data_snap : dataSnap.getChildren()) {
                                                    if (dataSnap.hasChild(itemObject.getItemID())) {
                                                        stringArrayList_take.add(data.getKey());
                                                    }
                                            //    }
                                            }
                                        }
                                    }
                                }
                            }
                            getGender();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });





            // linearLayout=(LinearLayout)findViewById(R.id.linearLayout);


            //  linearLayout=(LinearLayout)findViewById(R.id.linearLayout);


        }

//        if (itemObject.getItemID().equals(Object.getItemID())) {
//            if (itemObject.isTake_it_check()) {
//                stringArrayList_take.add(Object);
//            } else if (itemObject.isLeave_it_check()) {
//                stringArrayList_leave.add(Object);
//            }
//        }

        int[] data = {0, 1};
        int[] color = {Color.parseColor("#44d0dd"), Color.parseColor("#da59a8")};
//       // linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        //    pieChart_leave.addView(new CustomPieChart(getActivity(),2,data,color,itemObject.leaveit_count));
//
//
//
        //    int[] data1={6,5};

        //  linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        //    pieChart_take.addView(new CustomPieChart(getActivity(),2,data1,color1,itemObject.getTakeit_count()));

//        ArrayList<Entry> yvalues = new ArrayList<Entry>();
//        yvalues.add(new Entry(8f, 0));
//        yvalues.add(new Entry(15f, 1));
//
//        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");
//
//        ArrayList<String> xVals = new ArrayList<String>();
//
//        xVals.add("Male");
//        xVals.add("Female");
//
//
//        PieData data = new PieData(xVals, dataSet);
//        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        data.setValueFormatter(new PercentFormatter());
//        pieChart_leave.setData(data);
//
//
//        pieChart_take.setData(data);


        return view;
    }

    private void getGender() {
        final int[] Graph_data = {0, 0};
        final int counter_male = 0;
        final int counter_female = 0;

        //blue or pink
        final int[] color = {Color.parseColor("#44d0dd"), Color.parseColor("#da59a8")};
      //  final int[] color1 = {Color.parseColor("#da59a8"),Color.parseColor("#44d0dd")};



        FirebaseHandler.getInstance()
                .getUsersRef()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        pieChart_leave.removeAllViews();
                        pieChart_take.removeAllViews();
                        if(dataSnapshot!=null){
                            if(dataSnapshot.getValue()!=null){
                                AppLogs.d(TAG,""+dataSnapshot.getValue().toString());
                                for(DataSnapshot data:dataSnapshot.getChildren()){
                                    UserModel us = data.getValue(UserModel.class);
                                    if(stringArrayList_leave.contains(us.getUser_userID())){
                                        if (us.getUser_gender().equals("Male")) {
                                            Graph_data[0] = counter_male + 1;
                                            Graph_data[1] = counter_female;
                                            pieChart_leave.addView(new CustomPieChart(parent, 2, Graph_data, color, itemObject.getLeaveit_count()));
                                        } else {
                                            Graph_data[0] = counter_male;
                                            Graph_data[1] = counter_female + 1;
                                            pieChart_leave.addView(new CustomPieChart(parent, 2, Graph_data, color, itemObject.getLeaveit_count()));
                                        }
                                    }else if(stringArrayList_take.contains(us.getUser_userID())){
                                        if (us.getUser_gender().equals("Male")) {
                                            Graph_data[0] = counter_male + 1;
                                            Graph_data[1] = counter_female;
                                            pieChart_take.addView(new CustomPieChart(parent, 2, Graph_data, color, itemObject.getTakeit_count()));
                                        } else {
                                            Graph_data[0] = counter_male;
                                            Graph_data[1] = counter_female + 1;
                                            pieChart_take.addView(new CustomPieChart(parent, 2, Graph_data, color, itemObject.getTakeit_count()));
                                        }
                                    }
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        parent = (MainActivity) activity;
    }



}

//
//    gender_male_no = gender_male_no +1;
//            int[] data= new int[]{gender_male_no, gender_female_no};
//            if(gender_female_no==0){
//            int[] color = {Color.parseColor("#44d0dd")};
//            pieChart_leave.addView(new CustomPieChart(getActivity(),1,data,color,itemObject.getLeaveit_count()));
//
//            }else{
//
//            }
//            pieChart_leave.addView(new CustomPieChart(getActivity(),2,data,color,itemObject.getLeaveit_count()));
//            }else{
//            gender_female_no = gender_female_no +1;
//            int[] data1= new int[]{gender_male_no, gender_female_no};
//            pieChart_take.addView(new CustomPieChart(getActivity(),2,data1,color1,itemObject.getTakeit_count()));