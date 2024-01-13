package com.polytechnic.healthmanagement.UserHealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.UserHealth.DataBase.UserHealthDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddUserHealthActivity extends AppCompatActivity {
private TextView issueTV,descriptionTV;
private  String issueString,descriptionString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_health);



        issueTV=findViewById(R.id.issueUserHealth);
        descriptionTV=findViewById(R.id.descriptionUserHealth);
        Spinner problemRelatedToSpinner = findViewById(R.id.problemRelatedToSpinner);
        Button add=findViewById(R.id.add);

        LinearLayout linearLayout =findViewById(R.id.edit_delete_exit);
        LinearLayout linearLayoutMain =findViewById(R.id.linearLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDrawable);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ArrayList<String> problemRelatedToArrayList = new ArrayList<>();
        problemRelatedToArrayList.add("Malaria");
        problemRelatedToArrayList.add("Tuberculosis");
        problemRelatedToArrayList.add("Diabetes");
        problemRelatedToArrayList.add("Hypertension");
        problemRelatedToArrayList.add("Dengue Fever");
        problemRelatedToArrayList.add("Heart Disease");
        problemRelatedToArrayList.add("Stroke");
        problemRelatedToArrayList.add("Hepatitis");
        problemRelatedToArrayList.add("Typhoid Fever");
        problemRelatedToArrayList.add("Obstructive");
        ArrayAdapter problemRelatedToAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,problemRelatedToArrayList);
        problemRelatedToSpinner.setAdapter(problemRelatedToAdapter);


        if(getIntent().getBooleanExtra("flag",false)){
            linearLayoutMain.removeView(linearLayout);
            add.setOnClickListener(v ->{
                issueString=issueTV.getText().toString().trim();
                descriptionString=descriptionTV.getText().toString().trim();
                if(issueString.isEmpty()||descriptionString.isEmpty()){
                    if(issueString.isEmpty() && descriptionString.isEmpty())
                        Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
                    else if(issueString.isEmpty())
                        Toast.makeText(this, "Please enter issue ", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Please enter description ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, issueString+" "+descriptionString, Toast.LENGTH_SHORT).show();
                    UserHealthDB userHealthDB = new UserHealthDB(this);
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy h:mm:ss a"); // Add "ss" for seconds
                    String formattedDateTime = dateFormat.format(currentDate);
                    String date = formattedDateTime.substring(0, 8);
                    String time = formattedDateTime.substring(9);
                    if(userHealthDB.insert(issueString,descriptionString,problemRelatedToSpinner.getSelectedItem().toString(),date,time)){
                        Toast.makeText(this, "Insertion Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();                 }
                    else
                        Toast.makeText(this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            ImageView edit,delete,cancel;
            edit = findViewById(R.id.edit);
            delete =findViewById(R.id.delete);
            cancel=findViewById(R.id.cancel);
            linearLayout.removeView(cancel);

            int id=getIntent().getIntExtra("id",0);
            issueString=getIntent().getStringExtra("issue");
            descriptionString=getIntent().getStringExtra("description");
            String spinnerString =getIntent().getStringExtra("spinner");
            int swapInt = problemRelatedToArrayList.indexOf(spinnerString);
            String swapString = problemRelatedToArrayList.get(0);
            problemRelatedToArrayList.set(0,spinnerString);
            problemRelatedToArrayList.set(swapInt,swapString);
            problemRelatedToAdapter.notifyDataSetChanged();
            problemRelatedToSpinner.setAdapter(problemRelatedToAdapter);

            issueTV.setText(issueString);
            descriptionTV.setText(descriptionString);
            issueTV.setFocusable(false);
            issueTV.setClickable(false);
            issueTV.setFocusableInTouchMode(false);
            issueTV.setCursorVisible(false);
            issueTV.setTextColor(R.color.lightBlack);
            descriptionTV.setTextColor(R.color.lightBlack);
            descriptionTV.setFocusable(false);
            descriptionTV.setClickable(false);
            descriptionTV.setFocusableInTouchMode(false);
            descriptionTV.setCursorVisible(false);
            problemRelatedToSpinner.setEnabled(false);

            edit.setOnClickListener(v ->{
                issueTV.setFocusable(true);
                issueTV.setClickable(true);
                issueTV.setFocusableInTouchMode(true);
                issueTV.setCursorVisible(true);
                issueTV.setTextColor(R.color.black);
                descriptionTV.setTextColor(R.color.black);
                descriptionTV.setFocusable(true);
                descriptionTV.setClickable(true);
                descriptionTV.setFocusableInTouchMode(true);
                descriptionTV.setCursorVisible(true);
                problemRelatedToSpinner.setEnabled(true);
                Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
            });
            delete.setOnClickListener(v ->{
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
            });

        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}