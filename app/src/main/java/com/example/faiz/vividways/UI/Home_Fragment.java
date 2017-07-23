package com.example.faiz.vividways.UI;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.faiz.vividways.Adapters.ScrollingLinearLayout;
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
    public RecyclerView my_recycler_view;
    public static Home_Fragment home_fragment;
    public LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_layout,null);
        home_fragment = Home_Fragment.this;
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        MainActivity.appbar_TextView.setText("Home");
        MainActivity.Uploadbutton.setVisibility(View.VISIBLE);
        imageURL = new ArrayList<ItemObject>();

         my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(my_recycler_view);
       MainActivity.back_image.setVisibility(View.GONE);
        MainActivity.delete_image.setVisibility(View.GONE);
        MainActivity.report_image.setVisibility(View.GONE);
        my_recycler_view.setOnFlingListener(snapHelper);
        my_recycler_view.setHasFixedSize(true);


        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));





        SectionListDataAdapter adapter = new SectionListDataAdapter(getActivity(), imageURL);
      //  layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);

        int duration = getResources().getInteger(R.integer.scroll_duration);
        my_recycler_view.setLayoutManager(new ScrollingLinearLayout(getActivity(), LinearLayoutManager.HORIZONTAL, false, duration));
      //  my_recycler_view.setLayoutManager(layoutManager);
        my_recycler_view.setAdapter(adapter);


        return view;
    }


    public static Home_Fragment getInstance(){
        return home_fragment;
    }


}
