package com.bhjbestkalyangame.adminapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    private ArrayList<Fragment> mFragments;
    private ArrayList<String> titles;

    public ViewPagerAdapter(FragmentManager fn){
        super(fn);
        this.mFragments = new ArrayList<>();
        this.titles = new ArrayList<>();
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(Fragment mFragment, String title){
        mFragments.add(mFragment);
        titles.add(title);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
