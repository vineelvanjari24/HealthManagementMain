package com.polytechnic.healthmanagement.UserHealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
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

import com.google.android.material.textfield.TextInputLayout;
import com.polytechnic.healthmanagement.DoctorList.Fragment.DoctorListFragment;
import com.polytechnic.healthmanagement.MainActivity;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.UserHealth.DataBase.UserHealthDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddUserHealthActivity extends AppCompatActivity {
private TextView issueET,descriptionET;
private  String issueString,descriptionString;
public static String keyValue="null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_health);

        issueET=findViewById(R.id.issueUserHealth);
        descriptionET=findViewById(R.id.descriptionUserHealth);
        Spinner problemRelatedToSpinner = findViewById(R.id.problemRelatedToSpinner);
        Button add=findViewById(R.id.add);

        LinearLayout linearLayout =findViewById(R.id.edit_delete_exit);
        LinearLayout linearLayoutMain =findViewById(R.id.linearLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDrawable);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ArrayAdapter problemRelatedToAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,AddUserHealthActivity.probleRelatedToArrayList());
        problemRelatedToSpinner.setAdapter(problemRelatedToAdapter);

        UserHealthDB userHealthDB = new UserHealthDB(this);
         linearLayoutMain.removeView(linearLayout);
            add.setOnClickListener(v ->{
                issueString=issueET.getText().toString().trim();
                descriptionString=descriptionET.getText().toString().trim();
                if(issueString.isEmpty()||descriptionString.isEmpty()){
                    if(issueString.isEmpty() && descriptionString.isEmpty())
                        Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
                    else if(issueString.isEmpty())
                        Toast.makeText(this, "Please enter issue ", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Please enter description ", Toast.LENGTH_SHORT).show();
                }
                else {
                    keyValue=problemRelatedToSpinner.getSelectedItem().toString();
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy h:mm:ss a"); // Add "ss" for seconds
                    String formattedDateTime = dateFormat.format(currentDate);
                    String date = formattedDateTime.substring(0, 8);
                    String time = formattedDateTime.substring(9);
                        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Dialogbox_border);
                        builder.setMessage("DO YOU WANT TO RECOMMEND DOCTOR ??");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(!userHealthDB.insert(issueString,descriptionString,problemRelatedToSpinner.getSelectedItem().toString(),date,time)){
                                    Toast.makeText(AddUserHealthActivity.this, "Record added failed", Toast.LENGTH_SHORT).show();
                                }
                                    Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                  dialogInterface.dismiss();
                                  finish();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(!userHealthDB.insert(issueString,descriptionString,problemRelatedToSpinner.getSelectedItem().toString(),date,time)){
                                    Toast.makeText(AddUserHealthActivity.this, "Record added failed", Toast.LENGTH_SHORT).show();
                                }
                                Intent intent = new Intent();
                                setResult(2, intent);
                                dialogInterface.dismiss();
                                finish();
                            }
                        });
                        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog1, int which) {
                                dialog1.dismiss();
                            }
                        });
                        builder.show();
                }
            });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    public static  ArrayList probleRelatedToArrayList(){
        ArrayList<String> problemRelatedToArrayList = new ArrayList<>();
        problemRelatedToArrayList.add("related to nerves");
        problemRelatedToArrayList.add("related to lungs");
        problemRelatedToArrayList.add("Heart Related disease");
        problemRelatedToArrayList.add("Skin related disease");
        problemRelatedToArrayList.add("tooth");
        problemRelatedToArrayList.add("stomach related issues");
        problemRelatedToArrayList.add("normal fever and other related issues");
        return problemRelatedToArrayList;
    }
    public static HashMap<String,String> probleRelatedToDataMapping(){
        HashMap<String,String> map = new HashMap<>();
        map.put("Neurologist","related to nerves");
        map.put("pulmotologist","related to lungs");
        map.put("Cardiologist","Heart Related disease");
        map.put("Dermatologist","Skin related disease");
        map.put("Dentist","related to tooth");
        map.put("Gastroenterology","stomach related issues");
        map.put("General Doctor","normal fever and other related issues");
        return  map;
    }
    public static  String findKeyByValue(HashMap<String, String> map,String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null; // Key not found
    }
}
//Neurologist - related to nerves
//pulmotologist - related to lungs
//Cardiologist - Heart Related disease
//Dermatologist - Skin related disease
//Dentist - tooth
//Gastroenterology - stomach related issues
//General Doctor - normal fever and other related issues