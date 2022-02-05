package com.lk.bookshoppadminapp.UI;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lk.bookshoppadminapp.OptionMenuActivity;
import com.lk.bookshoppadminapp.R;

public class ProductActivity extends OptionMenuActivity {

    CardView addProduct,loadProduct,deleteProduct,updateProduct;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    BottomNavigationView navigationView;
    public ActionBarDrawerToggle toggle;
    public Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.product_layout);
        //navigationView = findViewById(R.id.bottomNavigationView2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product");

        //navigationView.setOnNavigationItemSelectedListener(this);
        addProduct = findViewById(R.id.product_add);
        loadProduct = findViewById(R.id.product_view);
        deleteProduct = findViewById(R.id.product_delete);
        updateProduct = findViewById(R.id.product_update);
        selectListener();

    }

    private void selectListener() {
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
        loadProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductActivity.this, ViewProductActivity.class);
                startActivity(intent);
            }
        });
        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, DeleteProductActivity.class);
                startActivity(intent);
            }
        });
        updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, UpdateProductActivity.class);
                startActivity(intent);
            }
        });

    }


}