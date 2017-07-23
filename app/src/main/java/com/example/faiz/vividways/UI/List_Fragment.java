package com.example.faiz.vividways.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faiz.vividways.Adapters.CustomizeViewPager;
import com.example.faiz.vividways.Adapters.TabAdapter;
import com.example.faiz.vividways.R;

import java.util.ArrayList;

/**
 * Created by Faiz on 7/23/2017.
 */

public class List_Fragment extends android.support.v4.app.Fragment {

   public View view;
    public TabLayout tabLayout;
    private CustomizeViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private Leave_Fragment tab1;
    private Take_Fragment tab2;
    public boolean list_choice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.list_layout,null);
        viewPager = (CustomizeViewPager) view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Leave it"));
        tabLayout.addTab(tabLayout.newTab().setText("Take it"));
        tab1 = new Leave_Fragment();
        tab2 = new Take_Fragment();
        fragments = new ArrayList<>();
        fragments.add(tab1);
        fragments.add(tab2);

        TabAdapter adapter = new TabAdapter(getActivity().getSupportFragmentManager(), fragments);
        //is line se tablayout k neche jo shade araaha hai woh change hoga pageviewer k mutabik
        viewPager.setAdapter(adapter);
        // viewPager.setOffscreenPageLimit(0);

        Bundle extras = getArguments();
        list_choice = extras.getBoolean("listb");


        if(list_choice){
             //take it
            viewPager.setCurrentItem(1);
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        }else{
            //leave it
            viewPager.setCurrentItem(0);
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
        }
        viewPager.setPagingEnabled(false);
//

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
//                    viewPager.endFakeDrag();
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });



        return view;
    }
}
