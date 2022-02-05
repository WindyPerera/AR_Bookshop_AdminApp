package com.lk.bookshoppadminapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lk.bookshoppadminapp.Adapters.PageAdapter;
import com.lk.bookshoppadminapp.Model.Invoice;
import com.lk.bookshoppadminapp.OptionMenuActivity;
import com.lk.bookshoppadminapp.R;

import java.util.List;

public class OrdersActivity extends OptionMenuActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    TabItem orderTab, cuzTab;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    TextView cuz_text, order_text;
    int cuzNum, orderNum;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        orderTab = (TabItem) findViewById(R.id.order_tab);
        cuzTab = (TabItem) findViewById(R.id.cuz_tab);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.toolbar);
        cuz_text = findViewById(R.id.customiz_count);
        order_text = findViewById(R.id.orders_count);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orders View");

        db.collection("Invoice").whereEqualTo("orderedStatus", "pending").whereEqualTo("paymentType", "customized").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Invoice> list = queryDocumentSnapshots.toObjects(Invoice.class);
                cuzNum = list.size();
                cuz_text.setText(String.valueOf(cuzNum));
            }
        });

        db.collection("Invoice").whereEqualTo("orderedStatus", "pending").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Invoice> list = queryDocumentSnapshots.toObjects(Invoice.class);
                int allCount = list.size();
                orderNum = allCount - cuzNum;
                order_text.setText(String.valueOf(orderNum));
            }
        });

        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0 || tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
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