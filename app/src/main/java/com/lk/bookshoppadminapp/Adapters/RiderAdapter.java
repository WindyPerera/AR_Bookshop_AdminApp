package com.lk.bookshoppadminapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lk.bookshoppadminapp.Fragment.AddNewRiderFragment;
import com.lk.bookshoppadminapp.Fragment.CuzOrderLoad;
import com.lk.bookshoppadminapp.Fragment.OrderPlaceFragment;
import com.lk.bookshoppadminapp.Fragment.OrdersLoad;
import com.lk.bookshoppadminapp.Fragment.ViewAllRidersFragment;

public class RiderAdapter extends FragmentPagerAdapter {

    int tabcount;

    public RiderAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.tabcount =behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new OrderPlaceFragment();
            case 1:
                return new AddNewRiderFragment();
            case 2:
                return new ViewAllRidersFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
