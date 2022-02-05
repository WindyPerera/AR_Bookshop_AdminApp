package com.lk.bookshoppadminapp.UI;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;
import com.lk.bookshoppadminapp.OptionMenuActivity;
import com.lk.bookshoppadminapp.R;

public class NavigationMenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.admin_home:

                Intent homeintent = new Intent(NavigationMenuActivity.this, AdminHome.class);
                startActivity(homeintent);

                return true;

            case R.id.view_profile:
                Intent prointent = new Intent(NavigationMenuActivity.this, AdminProfileActivity.class);
                startActivity(prointent);

                return true;
            case R.id.setting:

                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
