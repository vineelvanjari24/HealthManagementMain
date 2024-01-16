package com.polytechnic.healthmanagement.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.UserLogin.Login;

public class AdminMainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        toolbar =findViewById(R.id.toolbarDrawable);

        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.exit_option,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.exit){
            SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
            SharedPreferences.Editor edit = login.edit();
            edit.putBoolean("user",false);
            edit.putBoolean("admin",false);
            edit.apply();
            Intent intent = new Intent(this, Login.class);
            intent.putExtra("flag",false);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}