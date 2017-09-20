package com.example.faiz.vividways.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.faiz.vividways.Adapters.GridViewAdapter;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.Models.TopItems;
import com.example.faiz.vividways.Models.UserModel;
import com.example.faiz.vividways.R;
import com.example.faiz.vividways.UI.Activities.MainActivity;
import com.example.faiz.vividways.Utils.FirebaseHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Faiz on 7/20/2017.
 */

public class Top_Fragment extends android.support.v4.app.Fragment {

    public ArrayList<TopItems> itemObjectArrayList;
    public GridViewAdapter grid_view_adapter;
    public GridView grid_view_top5;
    public View view;
    public Spinner day_no, country_name;
    ArrayAdapter<String> AdapterCountry;
    String contry_array[] = {"My Country",
            "The World",
            "Country"
    };

    String days_array[] = {"Today",
            "Week",
            "Half Of Month",
            "Month", "Day"
    };
    public Query query;
    public String filter_country;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.top_layout, null);
        day_no = (Spinner) view.findViewById(R.id.day_no);
        country_name = (Spinner) view.findViewById(R.id.country_name);
        day_no.setPrompt("Days");
        country_name.setPrompt("Countries");
        itemObjectArrayList = new ArrayList<>();
        grid_view_top5 = (GridView) view.findViewById(R.id.top_5_list);
//        Locale[] locale = Locale.getAvailableLocales();
//        ArrayList<String> countries = new ArrayList<String>();
//        String country;
//        for( Locale loc : locale ){
//            country = loc.getDisplayCountry();
//            if( country.length() > 0 && !countries.contains(country) ){
//                countries.add( country );
//            }
//        }
//        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        grid_view_adapter = new GridViewAdapter(getActivity(), itemObjectArrayList);

        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, days_array) {

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };
        day_no.setAdapter(adapterDays);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day_no.setSelection(adapterDays.getCount());

        AdapterCountry = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, contry_array) {

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };
        country_name.setAdapter(AdapterCountry);
        AdapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country_name.setSelection(AdapterCountry.getCount());
        grid_view_top5.setAdapter(grid_view_adapter);
        MainActivity.appbar_TextView.setText("Top 5");
        MainActivity.Uploadbutton.setVisibility(View.GONE);


        Query query = FirebaseHandler.getInstance().getPostRef().orderByChild("takeit_count").startAt(1).endAt(100).limitToLast(5);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        //  AppLogs.d("TAG_top5", dataSnapshot.getValue().toString());
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            final ItemObject itemObject = data.getValue(ItemObject.class);

                        //    if (itemObject.getUserID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                        //    } else {

                                //    itemObjectArrayList.add(itemObject);
                                // grid_view_adapter.notifyDataSetChanged();
                                FirebaseHandler.getInstance().getUsersRef().child(itemObject.getUserID())
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot != null) {
                                                    if (dataSnapshot.getValue() != null) {
                                                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                                        itemObjectArrayList.add(new TopItems(userModel, itemObject));
                                                        grid_view_adapter.add(new TopItems(userModel, itemObject));
                                                        grid_view_adapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                            }

                            //     filterItems();
                        }
                    }
             //   }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        day_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (day_no.getSelectedItem().toString() != null) {
                    if (day_no.getSelectedItem().toString().equals("Day")) {

                    } else {
                        filterItems();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        country_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (country_name.getSelectedItem().toString() != null) {
                    if (country_name.getSelectedItem().toString().equals("Country")) {

                    } else {
                        if (country_name.getSelectedItem().toString().equals("My Country")) {
                            if (UserModel.getInstanceIfNotNull() != null) {
                                if (UserModel.getInstanceIfNotNull().getUser_country() != null) {
                                    filter_country = UserModel.getInstanceIfNotNull().getUser_country();
                                    String[] splitLocation = UserModel.getInstanceIfNotNull().getUser_country().split(",");
                                    if (splitLocation.length == 3) {
                                        filter_country = splitLocation[2];
                                    } else if (splitLocation.length == 2) {
                                        filter_country = splitLocation[1];
                                        // country_name.setSelection();
                                    } else {
                                        filter_country = splitLocation[0];
                                    }
                                }
                            }
                        } else if (country_name.getSelectedItem().toString().equals("The World")) {
                                    filter_country = "world";
                        }
                        filterItems();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


    public void filterItems() {


        if (day_no.getSelectedItem().toString() != null && !day_no.getSelectedItem().toString().equals("Day")) {
            if (country_name.getSelectedItem().toString() != null && !country_name.getSelectedItem().toString().equals("Country")) {
                if (day_no.getSelectedItem().toString().equals("Today")) {
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
                    String day = sdf.format(date);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(day);
                    grid_view_adapter.filterMembers(arrayList,filter_country, "today");
                } else if (day_no.getSelectedItem().toString().equals("Week")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
                    Calendar cal = Calendar.getInstance();
                    Date date = cal.getTime();
                    String days;
                    days = sdf.format(date);

                    ArrayList<String> arrayList = new ArrayList<>();
//               arrayList.add(days);

                    for (int i = 1; i < 8; i++) {
                        cal.add(Calendar.DAY_OF_MONTH, -1);
                        date = cal.getTime();
                        days = sdf.format(date);
                        arrayList.add(days);
                    }


                    //     for(int i = (days.length-1); i >= 0; i--){
                    //       System.out.println(days[i]);
                    //   }

                    grid_view_adapter.filterMembers(arrayList, filter_country, "Week");
                } else if (day_no.getSelectedItem().toString().equals("Half Of Month")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
                    Calendar cal = Calendar.getInstance();
                    Date date = cal.getTime();
                    String days;
                    days = sdf.format(date);


                    ArrayList<String> arrayList = new ArrayList<>();

                    for (int i = 1; i < 16; i++) {
                        cal.add(Calendar.DAY_OF_YEAR, -1);
                        date = cal.getTime();
                        days = sdf.format(date);
                        arrayList.add(days);
                    }


//               for(int i = (days.length-1); i >= 0; i--){
//                   System.out.println(days[i]);
//               }

                    grid_view_adapter.filterMembers(arrayList,filter_country, "Half Of Month");
                }
             else if (day_no.getSelectedItem().toString().equals("Month")) {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
                    Calendar cal = Calendar.getInstance();
                    Date date = cal.getTime();
                    String days;
                    days = sdf.format(date);


                    ArrayList<String> arrayList = new ArrayList<>();

                    for (int i = 1; i < 30; i++) {
                        cal.add(Calendar.DAY_OF_YEAR, -1);
                        date = cal.getTime();
                        days = sdf.format(date);
                        arrayList.add(days);
                    }


//               for(int i = (days.length-1); i >= 0; i--){
//                   System.out.println(days[i]);
//               }

                    grid_view_adapter.filterMembers(arrayList, filter_country, "Month");
                }
            } else {
                    Toast.makeText(getActivity(), "Select Country", Toast.LENGTH_SHORT).show();
            }
        } else {
               Toast.makeText(getActivity(), "Select Days", Toast.LENGTH_SHORT).show();
        }

    }

}
