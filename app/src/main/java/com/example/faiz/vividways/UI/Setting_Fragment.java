package com.example.faiz.vividways.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.faiz.vividways.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Faiz on 7/20/2017.
 */

public class Setting_Fragment extends android.support.v4.app.Fragment {

    public View view;
    public RelativeLayout relativeLayout,relativeLayout_logout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


       view = inflater.inflate(R.layout.setting_layout,null);

        MainActivity.appbar_TextView.setText("Setting");
        relativeLayout = (RelativeLayout)view.findViewById(R.id.discovery_pref);
        relativeLayout_logout = (RelativeLayout)view.findViewById(R.id.logout);


        relativeLayout_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });



        MainActivity.Uploadbutton.setVisibility(View.GONE);
        MainActivity.back_image.setVisibility(View.GONE);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 FragmentTransaction ft =  getActivity().getSupportFragmentManager()
                            .beginTransaction().setCustomAnimations(R.anim.in_from_right,R.anim.out_from_left,R.anim.out_from_left,R.anim.in_from_right);
                    MainActivity.Uploadbutton.setVisibility(View.GONE);
                    MainActivity.appbar_TextView.setText("Discovery Prefrences");
                    MainActivity.back_image.setVisibility(View.VISIBLE);
                    MainActivity.delete_image.setVisibility(View.GONE);
                    MainActivity.report_image.setVisibility(View.GONE);
                    ft.replace(R.id.fragment_container,new Discovery_Fragment());
                    ft.addToBackStack("");
                    ft.commit();
                }
            });




        return view;
    }
}
