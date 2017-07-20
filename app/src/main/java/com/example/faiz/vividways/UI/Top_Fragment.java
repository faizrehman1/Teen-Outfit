package com.example.faiz.vividways.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.faiz.vividways.R;

/**
 * Created by Faiz on 7/20/2017.
 */

public class Top_Fragment extends android.support.v4.app.Fragment {

    public View view;
    public Spinner day_no,country_name;
    String contry_array[] = {"Country","Malaysia",
            "United States",
            "Indonesia",
            "France",
            "Italy",
            "Singapore",
            "New Zealand",
            "India",};

    String days_array[] = {"day","1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14","15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "24",
            "25",
            "26",
            "27",
            "28","29","30"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.top_layout, null);
        day_no = (Spinner)view.findViewById(R.id.day_no);
        country_name = (Spinner)view.findViewById(R.id.country_name);


        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, days_array);

        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        day_no.setAdapter(adapterDays);

        ArrayAdapter<String> AdapterCountry = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, contry_array);

        AdapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        country_name.setAdapter(AdapterCountry);

        MainActivity.appbar_TextView.setText("Top 5");
        MainActivity.Uploadbutton.setVisibility(View.GONE);
        return view;
    }
}
