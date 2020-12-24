package com.example.myapplication.ui.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.Fragments.InfoFragment;
import com.example.myapplication.Fragments.ModelFragment;
import com.example.myapplication.Fragments.ScanFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    // tab titles
    private String[] tabTitles = new String[]{"Scan", "Activity", "Info"};

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ScanFragment.newInstance("asd", "asd");
            case 1:
                return ModelFragment.newInstance("asd", "asd");
            case 2:
                return InfoFragment.newInstance("asd", "asd");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}