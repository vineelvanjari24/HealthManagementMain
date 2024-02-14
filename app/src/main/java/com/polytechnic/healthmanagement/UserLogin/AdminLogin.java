package com.polytechnic.healthmanagement.UserLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.polytechnic.healthmanagement.Admin.AdminMainActivity;
import com.polytechnic.healthmanagement.R;

public class AdminLogin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText username,password;
        username = findViewById(R.id.emailID);
        password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
            loginButton.setOnClickListener(v ->{
                String usernameString = username.getText().toString().trim();
                String passwordString = password.getText().toString().trim();
                if(usernameString.isEmpty()  || passwordString.isEmpty()){
                    if(usernameString.isEmpty()  && passwordString.isEmpty())
                        Toast.makeText(this, "Enter Username and Password", Toast.LENGTH_SHORT).show();
                    else if(usernameString.isEmpty())
                        Toast.makeText(this, "Enter Username !!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Enter Password !!", Toast.LENGTH_SHORT).show();
                }
                else if (usernameString.equals("admin")) {
                    if(passwordString.equals("admin")) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                        SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
                        SharedPreferences.Editor editor = login.edit();
                        editor.putBoolean("admin",true);
                        editor.putBoolean("user",false);
                        editor.apply();
                        startActivity(new Intent(this, AdminMainActivity.class));
                        finish();
                    }
                    else
                        Toast.makeText(this, "Wrong Password !!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Admin doesn't exist !!", Toast.LENGTH_SHORT).show();
                }
            });
    }
}