package com.lk.bookshoppadminapp;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.lk.bookshoppadminapp.UI.AdminProfileActivity;

public class OptionMenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.admin_home:

                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.view_profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.setting:

                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.admin_home:

                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.view_profile:

                Intent prointent = new Intent(OptionMenuActivity.this, AdminProfileActivity.class);
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
