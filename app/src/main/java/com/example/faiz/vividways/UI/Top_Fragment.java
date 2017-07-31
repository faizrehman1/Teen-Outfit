package com.example.faiz.vividways.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.faiz.vividways.Adapters.GridViewAdapter;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.R;
import com.example.faiz.vividways.UI.Activities.MainActivity;

import java.util.ArrayList;

/**
 * Created by Faiz on 7/20/2017.
 */

public class Top_Fragment extends android.support.v4.app.Fragment {

    public ArrayList<ItemObject> itemObjectArrayList;
    public GridViewAdapter grid_view_adapter;
    public GridView grid_view_top5;
    public View view;
    public Spinner day_no,country_name;
    String contry_array[] = {"Afghanistan",
            "Albania",
            "Algeria",
            "Andorra",
            "Angola",
            "Antigua & Barbuda",
            };

    String days_array[] = {"1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
           };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.top_layout, null);
        day_no = (Spinner)view.findViewById(R.id.day_no);
        country_name = (Spinner)view.findViewById(R.id.country_name);
        itemObjectArrayList  = new ArrayList<>();
        grid_view_top5 = (GridView)view.findViewById(R.id.top_5_list);


        itemObjectArrayList.add(new ItemObject("","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2F8519984.jpg?alt=media&token=01e871c0-7c8d-46ed-b1cb-b06b861aafe2"));
        itemObjectArrayList.add(new ItemObject("","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fasa.jpg?alt=media&token=c4bd1517-4fe3-44bb-8a92-9af741dd52a7"));
        itemObjectArrayList.add(new ItemObject("","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fasas.jpg?alt=media&token=e9032b88-4db7-4bf0-a157-a38a78488ed8"));
        itemObjectArrayList.add(new ItemObject("","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2FFH3_Tamo_Racemo_Vista.png?alt=media&token=74c5ecf8-b94d-444a-ae36-aa9fec9b94f2"));
        itemObjectArrayList.add(new ItemObject("","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fsasa.jpg?alt=media&token=8a418970-9e67-443c-a812-24a9d29c8f12"));
        itemObjectArrayList.add(new ItemObject("","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fsuper-cars.png?alt=media&token=03d3c9c5-11a7-4941-9184-0ca691b5fe94"));
        itemObjectArrayList.add(new ItemObject("","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fmaxresdefault.jpg?alt=media&token=12eacaf1-0e48-49ea-8fb7-3789e6f9e712"));
        itemObjectArrayList.add(new ItemObject("","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2FFH3_Tamo_Racemo_Vista.png?alt=media&token=74c5ecf8-b94d-444a-ae36-aa9fec9b94f2"));
        itemObjectArrayList.add(new ItemObject("","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fasas.jpg?alt=media&token=e9032b88-4db7-4bf0-a157-a38a78488ed8"));
        itemObjectArrayList.add(new ItemObject("","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fas.jpg?alt=media&token=d6a071d9-840e-4980-93e7-917100312614"));
        itemObjectArrayList.add(new ItemObject("","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fasa.jpg?alt=media&token=c4bd1517-4fe3-44bb-8a92-9af741dd52a7"));
        itemObjectArrayList.add(new ItemObject("","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2F8519984.jpg?alt=media&token=01e871c0-7c8d-46ed-b1cb-b06b861aafe2"));

        grid_view_adapter = new GridViewAdapter(getActivity(),itemObjectArrayList);

        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, days_array);

        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        day_no.setAdapter(adapterDays);

        ArrayAdapter<String> AdapterCountry = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, contry_array);

        AdapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grid_view_top5.setAdapter(grid_view_adapter);
        country_name.setAdapter(AdapterCountry);

        MainActivity.appbar_TextView.setText("Top 5");
        MainActivity.Uploadbutton.setVisibility(View.GONE);
        return view;
    }
}
