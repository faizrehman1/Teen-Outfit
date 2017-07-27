package com.example.faiz.vividways.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faiz.vividways.Adapters.ProfileGridAdapter;
import com.example.faiz.vividways.Adapters.RatingBarView;
import com.example.faiz.vividways.AppLogs;
import com.example.faiz.vividways.FirebaseHandler;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Faiz on 7/20/2017.
 */

public class Profile_Fragment extends android.support.v4.app.Fragment {

    private static final String TAG = "Profile_Fragment";
    public View view;
    public GridView gridView;
    public ProfileGridAdapter profileGridAdapter;
    public ArrayList<ItemObject> itemObjectArrayList;
    RatingBar ratingBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profile_layout,null);

        gridView = (GridView)view.findViewById(R.id.grid_view_profile);
        MainActivity.appbar_TextView.setText("Profile");
        MainActivity.Uploadbutton.setVisibility(View.GONE);
        itemObjectArrayList = new ArrayList<>();

        final RatingBarView ratingBarView = (RatingBarView)view.findViewById(R.id.starView);
        ratingBarView.setmClickable(true);
        //you can set up view here or in XML

        //ratingBarView.setStarCount(5);
        //ratingBarView.setStarEmptyDrawable(...);
        //ratingBarView.setStarFillDrawable(...);
        //ratingBarView.setStarImageSize();

        //bind some data
        ratingBarView.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject,int RatingScore) {
                Toast.makeText(getActivity() ,"bindObject : "+bindObject,Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseHandler.getInstance().getUser_postRef().child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    AppLogs.d(TAG, dataSnapshot.getValue().toString());
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        ItemObject object = dataSnapshot1.getValue(ItemObject.class);
                        itemObjectArrayList.add(object);
                        profileGridAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        profileGridAdapter = new ProfileGridAdapter(itemObjectArrayList,getActivity());
        gridView.setAdapter(profileGridAdapter);

        return view;
    }
}
