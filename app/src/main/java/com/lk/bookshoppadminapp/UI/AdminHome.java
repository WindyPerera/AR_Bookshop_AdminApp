package com.lk.bookshoppadminapp.UI;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lk.bookshoppadminapp.OptionMenuActivity;
import com.lk.bookshoppadminapp.R;

public class AdminHome extends NavigationMenuActivity {

    CardView orderView, productView, ridersView, reportsView;
    TextView adminName;
    ImageView adminImage;
    String adminID;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    public ActionBarDrawerToggle toggle;
    BottomNavigationView navigationView;
//    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        orderView = findViewById(R.id.orders_view);
        productView = findViewById(R.id.product_view);
        ridersView = findViewById(R.id.riders_view);
        adminName = findViewById(R.id.admin_name);

        Bundle bundle = getIntent().getExtras();
        adminID = bundle.getString("adminID");
        adminName.setText(bundle.getString("adminName"));

        adminListener();
        drawerLayout = findViewById(R.id.admin_home);
        navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView2);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//        navigationView.setOnNavigationItemSelectedListener(this);

//        adminImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AdminHome.this, AdminProfileActivity.class);
//                intent.putExtra("adminID",adminID );
//                startActivity(intent);
//
//
//            }
//        });

    }


    private void adminListener() {

        orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, OrdersActivity.class);
                startActivity(intent);
            }
        });
        productView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, ProductActivity.class);
                startActivity(intent);
            }
        });
        ridersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, RidersActivity.class);
                startActivity(intent);
            }
        });
//        reportsView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AdminHome.this, ReportActivity.class);
//                startActivity(intent);
//            }
//        });

    }

}