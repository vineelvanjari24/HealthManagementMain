package com.polytechnic.healthmanagement.UserLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.polytechnic.healthmanagement.MainActivity;
import com.polytechnic.healthmanagement.R;

public class LoginPage extends AppCompatActivity {

    CardView admin, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        admin = findViewById(R.id.admin);
        user = findViewById(R.id.user);
        SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = login.edit();

        admin.setOnClickListener(v -> {
            Intent intentAdminLogin = new Intent(getApplicationContext(), AdminLogin.class);
            startActivity(intentAdminLogin);
            finish();
        });

        user.setOnClickListener(v -> {
            Intent intentUserLogin = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentUserLogin);
            editor.putBoolean("user",true);
            editor.putBoolean("admin",false);
            finish();
            editor.apply();
        });

    }
}