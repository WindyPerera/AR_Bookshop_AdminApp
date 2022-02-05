package com.lk.bookshoppadminapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lk.bookshoppadminapp.Fragment.AddNewProduct;

public class ProductAddAdapter extends FragmentPagerAdapter {

    int tabcount;

    public ProductAddAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.tabcount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AddNewProduct();

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
