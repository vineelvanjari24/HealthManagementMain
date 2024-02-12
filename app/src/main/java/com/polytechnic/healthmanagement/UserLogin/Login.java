package com.polytechnic.healthmanagement.UserLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.polytechnic.healthmanagement.Admin.AdminMainActivity;
import com.polytechnic.healthmanagement.MainActivity;
import com.polytechnic.healthmanagement.R;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText username,password;
        TextView loginTV,changeTV;
        ImageView activityImage=findViewById(R.id.activityImage);
        loginTV=findViewById(R.id.loginText);
        username = findViewById(R.id.emailID);
        password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        changeTV=findViewById(R.id.changeTV);

        SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
        if(login.getBoolean("admin",false)){
            startActivity(new Intent(this, AdminMainActivity.class));
            finish();
        }
        else if(login.getBoolean("user",false))
        {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        if(getIntent().getBooleanExtra("flag",true)){

            UserLoginDB UserLoginDb = new UserLoginDB(this);
            loginButton.setOnClickListener(v ->{

                        String usernameString = username.getText().toString().trim();
                        String passwordString = password.getText().toString().trim();
                        if(usernameString.isEmpty()  || passwordString.isEmpty()){
                            if(usernameString.isEmpty()  && passwordString.isEmpty())
                                Toast.makeText(this, "Enter Username and Password", Toast.LENGTH_SHORT).show();
                            if(usernameString.isEmpty())
                                Toast.makeText(this, "Enter Username !!", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(this, "Enter Password !!", Toast.LENGTH_SHORT).show();
                        }
                        else if (UserLoginDb.UsernameChecked(usernameString)) {
                            if(UserLoginDb.loginCheck(usernameString,passwordString)) {
                                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor edit = login.edit();
                                edit.putBoolean("user",true);
                                edit.putBoolean("admin",false);
                                edit.apply();
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            }
                            else
                                Toast.makeText(this, "Wrong Password !!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "User doesn't exist !!", Toast.LENGTH_SHORT).show();
                        }
                    });
                findViewById(R.id.register).setOnClickListener(v ->{
                    startActivity(new Intent(getApplicationContext(),RegisterUser.class));
            });
            changeTV.setOnClickListener(v ->{
                backPress(false);
            });
        }
        else {
            loginTV.setText("ADMIN LOGIN");
            changeTV.setText("User");
            activityImage.setImageResource(R.drawable.admin);
            ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
            LinearLayout layoutLayout = findViewById(R.id.linearLayout);
            constraintLayout.removeView(layoutLayout);
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
                        SharedPreferences.Editor edit = login.edit();
                        edit.putBoolean("user",false);
                        edit.putBoolean("admin",true);
                        edit.apply();
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
            changeTV.setOnClickListener(v ->{
                backPress(true);
            });
        }
    }

    public void backPress(boolean flag){
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("flag",flag);
        startActivity(intent);
        finish();
    }
}