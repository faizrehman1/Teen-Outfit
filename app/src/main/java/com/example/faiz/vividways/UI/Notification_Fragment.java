package com.example.faiz.vividways.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faiz.vividways.R;

/**
 * Created by Faiz on 7/20/2017.
 */

public class Notification_Fragment extends android.support.v4.app.Fragment {

    public View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.notification_layout,null);

        MainActivity.appbar_TextView.setText("Notification");
        MainActivity.Uploadbutton.setVisibility(View.GONE);
        return view;
    }
}
