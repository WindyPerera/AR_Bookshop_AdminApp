package com.lk.bookshoppadminapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lk.bookshoppadminapp.Fragment.CustomisedView;
import com.lk.bookshoppadminapp.Fragment.CuzOrderLoad;
import com.lk.bookshoppadminapp.Fragment.OrderViewFragment;
import com.lk.bookshoppadminapp.Fragment.OrdersLoad;

public class PageAdapter extends FragmentPagerAdapter {

    private int tabcount;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.tabcount =behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new OrdersLoad();
            case 1:
                return new CuzOrderLoad();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
