package com.polytechnic.healthmanagement.DoctorList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.UserHealth.AddUserHealthActivity;

public class DoctorActivity extends AppCompatActivity {
    String nameString,specificationString,experienceString,probelmRelatedString,descriptionString,workinInString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        Button add = findViewById(R.id.add);
        EditText nameET,specificationET,experienceET,descriptionET,workingInET;
        nameET=findViewById(R.id.doctorName);
        specificationET=findViewById(R.id.doctorSpecification);
        experienceET=findViewById(R.id.doctorExperience);
        descriptionET=findViewById(R.id.doctorDescription);
        workingInET=findViewById(R.id.doctorWorkingIn);
        Spinner problemRelatedToSpinner =findViewById(R.id.problemRelatedToSpinner);
        ArrayAdapter problemRelatedToAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, AddUserHealthActivity.probleRelatedToArrayList());
        problemRelatedToSpinner.setAdapter(problemRelatedToAdapter);
        add.setText("ADD");

        DoctorDB doctorDB = new DoctorDB(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameString=nameET.getText().toString().trim();
                specificationString=specificationET.getText().toString().trim();
                experienceString=experienceET.getText().toString().trim();
                descriptionString=descriptionET.getText().toString().trim();
                workinInString=workingInET.getText().toString().trim();
                if(nameString.isEmpty() || specificationString.isEmpty() || experienceString.isEmpty() || descriptionString.isEmpty() ||workinInString.isEmpty()){
                    if(nameString.isEmpty() && specificationString.isEmpty() && experienceString.isEmpty() && descriptionString.isEmpty())
                        Toast.makeText(DoctorActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    else if (nameString.isEmpty())
                        Toast.makeText(DoctorActivity.this, "Enter name of the doctor", Toast.LENGTH_SHORT).show();
                    else if (specificationString.isEmpty())
                        Toast.makeText(DoctorActivity.this, "Enter specification of the doctor", Toast.LENGTH_SHORT).show();
                    else if (experienceString.isEmpty())
                        Toast.makeText(DoctorActivity.this, "Enter experience of the doctor", Toast.LENGTH_SHORT).show();
                    else if (workinInString.isEmpty())
                        Toast.makeText(DoctorActivity.this, "Enter working place of the doctor", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(DoctorActivity.this, "Enter description of the doctor", Toast.LENGTH_SHORT).show();
                }
                else {
                    probelmRelatedString=problemRelatedToSpinner.getSelectedItem().toString();
                    if(doctorDB.insert(nameString,descriptionString,specificationString,experienceString,workinInString,probelmRelatedString)){
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else
                        Toast.makeText(DoctorActivity.this, "Doctor Added Failed", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }
}