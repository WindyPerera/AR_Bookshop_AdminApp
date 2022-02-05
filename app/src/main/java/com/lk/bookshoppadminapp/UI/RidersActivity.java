package com.lk.bookshoppadminapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.lk.bookshoppadminapp.Adapters.PageAdapter;
import com.lk.bookshoppadminapp.Adapters.RiderAdapter;
import com.lk.bookshoppadminapp.R;

public class RidersActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    TabItem placeOrder,newRider,viewRiders;
    ViewPager viewPager;
    RiderAdapter riderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riders);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rider");

        tabLayout = (TabLayout)findViewById(R.id.riderTab_layout);
        placeOrder = (TabItem)findViewById(R.id.placeOrder_tab);
        newRider = (TabItem)findViewById(R.id.regiRider_tab);
        viewRiders = (TabItem)findViewById(R.id.viewRider_tab);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        riderAdapter =new RiderAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(riderAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()==0|| tab.getPosition()==1||tab.getPosition()==2){
                    riderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // listener for page change
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}