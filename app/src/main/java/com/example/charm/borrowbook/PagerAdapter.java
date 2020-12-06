package com.example.charm.borrowbook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new LibraryFragment();
            case 1: return new BorrowFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
