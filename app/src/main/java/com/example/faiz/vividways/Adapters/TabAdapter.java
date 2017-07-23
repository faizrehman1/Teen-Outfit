package com.example.faiz.vividways.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Faiz on 7/23/2017.
 */

public class TabAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;

    public TabAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {

        //han bhai..jo tumhara single frag hai usky layout me jao

        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
