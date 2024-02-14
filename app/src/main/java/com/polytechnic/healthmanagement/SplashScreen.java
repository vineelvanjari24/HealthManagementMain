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
                Intent intent;
                SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
                if(!login.getBoolean("user",false) && !login.getBoolean("admin",false)){
                    intent = new Intent(SplashScreen.this, LoginPage.class);
                }
                else if(login.getBoolean("user",false)){
                    intent = new Intent(SplashScreen.this, MainActivity.class);
                }
                else{
                    intent = new Intent(SplashScreen.this, AdminMainActivity.class);
                }
                startActivity(intent);  //starts specified intent
                finish();     //finish() finishes this activity
            }
        }, 1000);     //delayMillis is the milliseconds time the splash should be shown
    }
    }
