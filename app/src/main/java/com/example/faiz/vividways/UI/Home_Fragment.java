package com.example.faiz.vividways.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.faiz.vividways.Adapters.SectionListDataAdapter;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Faiz on 7/20/2017.
 */

public class Home_Fragment extends android.support.v4.app.Fragment {

    public View view;
    public ViewPager mViewPager;
    private  DatabaseReference firebase;
    private FirebaseAuth mAuth;
    private ArrayList<ItemObject> imageURL;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_layout,null);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        MainActivity.appbar_TextView.setText("Home");
        MainActivity.Uploadbutton.setVisibility(View.VISIBLE);
        imageURL = new ArrayList<ItemObject>();

        RecyclerView my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);


        imageURL.add(new ItemObject("Faiz","gs://vividways-1675b.appspot.com/profileImages/Screenshot_2017-07-14-19-08-55Wed Jul 19 20:24:01 GMT+05:00 2017.png"));
        imageURL.add(new ItemObject("Faiz","gs://vividways-1675b.appspot.com/profileImages/Screenshot_2017-07-14-19-08-55Wed Jul 19 20:24:01 GMT+05:00 2017.png"));
        imageURL.add(new ItemObject("Faiz","gs://vividways-1675b.appspot.com/profileImages/Screenshot_2017-07-14-19-08-55Wed Jul 19 20:24:01 GMT+05:00 2017.png"));
        imageURL.add(new ItemObject("Faiz","gs://vividways-1675b.appspot.com/profileImages/Screenshot_2017-07-14-19-08-55Wed Jul 19 20:24:01 GMT+05:00 2017.png"));
        imageURL.add(new ItemObject("Faiz","gs://vividways-1675b.appspot.com/profileImages/Screenshot_2017-07-14-19-08-55Wed Jul 19 20:24:01 GMT+05:00 2017.png"));
        imageURL.add(new ItemObject("Faiz","gs://vividways-1675b.appspot.com/profileImages/Screenshot_2017-07-14-19-08-55Wed Jul 19 20:24:01 GMT+05:00 2017.png"));
        imageURL.add(new ItemObject("Faiz","gs://vividways-1675b.appspot.com/profileImages/Screenshot_2017-07-14-19-08-55Wed Jul 19 20:24:01 GMT+05:00 2017.png"));
        imageURL.add(new ItemObject("Faiz","gs://vividways-1675b.appspot.com/profileImages/Screenshot_2017-07-14-19-08-55Wed Jul 19 20:24:01 GMT+05:00 2017.png"));
        imageURL.add(new ItemObject("Faiz","gs://vividways-1675b.appspot.com/profileImages/Screenshot_2017-07-14-19-08-55Wed Jul 19 20:24:01 GMT+05:00 2017.png"));
        imageURL.add(new ItemObject("Faiz","gs://vividways-1675b.appspot.com/profileImages/Screenshot_2017-07-14-19-08-55Wed Jul 19 20:24:01 GMT+05:00 2017.png"));
        imageURL.add(new ItemObject("Faiz","gs://vividways-1675b.appspot.com/profileImages/Screenshot_2017-07-14-19-08-55Wed Jul 19 20:24:01 GMT+05:00 2017.png"));


        SectionListDataAdapter adapter = new SectionListDataAdapter(getActivity(), imageURL);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        my_recycler_view.setAdapter(adapter);







        return view;
    }


}
