package com.polytechnic.healthmanagement.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.UserLogin.AdminLogin;
import com.polytechnic.healthmanagement.UserLogin.LoginPage;

public class AdminMainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        toolbar =findViewById(R.id.toolbarDrawable);
        toolbar.setTitle("Admin");
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(viewPagerAdapter) ;
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exit_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.exit){
            SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
            SharedPreferences.Editor edit = login.edit();
            edit.putBoolean("user",false);
            edit.putBoolean("admin",false);
            edit.apply();
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}