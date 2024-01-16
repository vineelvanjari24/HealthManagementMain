package com.polytechnic.healthmanagement.UserLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.polytechnic.healthmanagement.R;

public class RegisterUser extends AppCompatActivity {
    EditText username,password,reEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        username=findViewById(R.id.emailID);
        password=findViewById(R.id.password);
        reEnterPassword=findViewById(R.id.reEnterPassword);
        findViewById(R.id.registerButton).setOnClickListener(v ->{

            String usernameString = username.getText().toString().trim();
            String passwordString = password.getText().toString().trim();
            String reEnterPasswordString = reEnterPassword.getText().toString();
            UserLoginDB userLoginDB = new UserLoginDB(this);
            if(usernameString.isEmpty() || passwordString.isEmpty() || reEnterPasswordString.isEmpty() ){
                if(usernameString.isEmpty() && passwordString.isEmpty() && reEnterPasswordString.isEmpty())
                    Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
                else if(usernameString.isEmpty() )
                    Toast.makeText(this, "Enter Email ID", Toast.LENGTH_SHORT).show();
                else if(passwordString.isEmpty() )
                    Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Re-Enter Password", Toast.LENGTH_SHORT).show();
            }else if (!userLoginDB.UsernameChecked(usernameString)){
                if((passwordString.length()<6)){
                    Toast.makeText(this, "Password should be greater than 6 char", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(passwordString.equals(reEnterPasswordString)){
                        if(userLoginDB.userInsert(usernameString,passwordString)) {
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                            Toast.makeText(this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(this, "Re enter password mismatched", Toast.LENGTH_SHORT).show();
                }
              }
            else
                Toast.makeText(this, "Username Already existed ... please enter unique name ", Toast.LENGTH_SHORT).show();
        });
    }


}