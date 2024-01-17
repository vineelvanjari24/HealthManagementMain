package com.polytechnic.healthmanagement.DoctorList.Fragment;


import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.healthmanagement.DoctorList.DoctorDB;
import com.polytechnic.healthmanagement.DoctorList.DoctorActivity;
import com.polytechnic.healthmanagement.DoctorList.RecycleView.DoctorAdapter;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.UserHealth.RecycleView.UserHealthAdapter;

import java.util.ArrayList;


public class DoctorListFragment extends Fragment {
    private static final int ADD_ITEM_REQUEST = 1;
    DoctorAdapter doctorAdapter;
    RecyclerView recyclerView;
Context context;
String resource;
    public DoctorListFragment() {
        // Required empty public constructor
    }
    public DoctorListFragment(Context context,String resource) {
        this.context=context;
        this.resource=resource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_doctor_list, container, false);

        RelativeLayout relativeLayout = view.findViewById(R.id.relativeLayout);
        CardView add =view.findViewById(R.id.addDoctorList);
        if(resource.equals("fromMainActivity")){
            Toast.makeText(context, "MainActivity", Toast.LENGTH_SHORT).show();
            relativeLayout.removeView(add);
            doctorAdapter = new DoctorAdapter(context,"fromMainActivity");
        }
        else if(resource.equals("fromAdmin")){
            Toast.makeText(context, "Admin", Toast.LENGTH_SHORT).show();
            add.setOnClickListener(vv ->{
                Intent intent = new Intent(context, DoctorActivity.class);
                doctorAdapter = new DoctorAdapter(context,"fromMainActivity");
                startActivityForResult(intent, ADD_ITEM_REQUEST);
                doctorAdapter = new DoctorAdapter(context,"fromAdmin");
            });

        } else if (resource.equals("fromUser")) {
            Toast.makeText(context, "user", Toast.LENGTH_SHORT).show();
            relativeLayout.removeView(add);
            doctorAdapter = new DoctorAdapter(context,"fromUser");
        }


         recyclerView  = view.findViewById(R.id.doctorListRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        SharedPreferences login = context.getSharedPreferences("login",MODE_PRIVATE);
        if(login.getBoolean("doctorList",true)){
            DoctorDB doctorDB = new DoctorDB(context);
            doctorDB.insert("vignesh","doctor since 2010 ","Cardiologists","14","Yoshada","Malaria");
            doctorDB.insert("akber","doctor since 2010 ","Cardiologists","14","Yoshada","Malaria");
            doctorDB.insert("varun","doctor since 2010 ","Cardiologists","14","Yoshada","Malaria");
            doctorDB.insert("sruthilaya","doctor since 2010 ","Cardiologists","14","Yoshada","Malaria");
            doctorDB.insert("vineel","doctor since 2010 ","Cardiologists","14","Yoshada","Malaria");
            SharedPreferences.Editor edit = login.edit();
            edit.putBoolean("doctorList",false);
            edit.apply();
        }

        recyclerView.setAdapter(doctorAdapter);


        return  view;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_REQUEST && resultCode == RESULT_OK) {
            doctorAdapter = new DoctorAdapter(context ,"fromAdmin");
            recyclerView.setAdapter(doctorAdapter);
        }

    }

}