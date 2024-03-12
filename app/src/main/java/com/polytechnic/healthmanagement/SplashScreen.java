package com.polytechnic.healthmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.polytechnic.healthmanagement.Admin.AdminMainActivity;
import com.polytechnic.healthmanagement.UserLogin.LoginPage;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {    //Handler is used run the thread after specified time finishes
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, OnBoardingTutorial.class));
                finish();
            }
        }, 1);
        }

    }
