package com.example.faiz.vividways.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.faiz.vividways.Adapters.ProfileGridAdapter;
import com.example.faiz.vividways.Adapters.RatingBarView;
import com.example.faiz.vividways.AppLogs;
import com.example.faiz.vividways.FirebaseHandler;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.Models.UserModel;
import com.example.faiz.vividways.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
    public MainActivity mainActivity;
    public CircleImageView user_profile_img;
    public TextView user_name_profile,user_city;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profile_layout,null);
        user_profile_img = (CircleImageView)view.findViewById(R.id.user_image_profile);
        gridView = (GridView)view.findViewById(R.id.grid_view_profile);
        user_name_profile = (TextView)view.findViewById(R.id.user_name_profile);
        user_city = (TextView)view.findViewById(R.id.user_city);
        MainActivity.appbar_TextView.setText("Profile");
        MainActivity.Uploadbutton.setVisibility(View.GONE);
        MainActivity.back_image.setVisibility(View.GONE);
        MainActivity.report_image.setVisibility(View.GONE);
        MainActivity.delete_image.setVisibility(View.GONE);
        itemObjectArrayList = new ArrayList<>();
        mainActivity = (MainActivity) getActivity();
        final RatingBarView ratingBarView = (RatingBarView)view.findViewById(R.id.starView);
        ratingBarView.setmClickable(true);
        //you can set up view here or in XML

        FirebaseHandler.getInstance()
                .getUsersRef()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Glide.get(getActivity()).clearMemory();
                        if(dataSnapshot!=null){
                            if(dataSnapshot.getValue()!=null){
                                UserModel userModel  = dataSnapshot.getValue(UserModel.class);
                                Glide.with(getActivity()).load(userModel.getUser_imgURL()).placeholder(R.mipmap.placeholder).into(user_profile_img);
                                user_name_profile.setText(userModel.getUser_fname()+" "+userModel.getUser_lname());
                                user_city.setText(userModel.getUser_country());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


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
                itemObjectArrayList.clear();
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


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("clickItem",itemObjectArrayList.get(position));
                Statistic_Fragment statistic_fragment = new Statistic_Fragment();
                statistic_fragment.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager()
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


        return view;
    }
}
