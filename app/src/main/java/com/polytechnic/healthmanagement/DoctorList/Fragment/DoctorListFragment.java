package com.polytechnic.healthmanagement.DoctorList.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.healthmanagement.DoctorList.DoctorDB;
import com.polytechnic.healthmanagement.DoctorList.RecycleView.DoctorAdapter;
import com.polytechnic.healthmanagement.R;


public class DoctorListFragment extends Fragment {

Context context;
    public DoctorListFragment() {
        // Required empty public constructor
    }
    public DoctorListFragment(Context context) {
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_doctor_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.doctorListRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DoctorAdapter doctorAdapter = new DoctorAdapter(context);
        recyclerView.setAdapter(doctorAdapter);
        if(false){
            DoctorDB doctorDB = new DoctorDB(context);
            doctorDB.insert("vignesh","doctor since 2010 ","Cardiologists","14","Yoshada");
            doctorDB.insert("akber","doctor since 2010 ","Cardiologists","14","Yoshada");
            doctorDB.insert("varun","doctor since 2010 ","Cardiologists","14","Yoshada");
            doctorDB.insert("sruthilaya","doctor since 2010 ","Cardiologists","14","Yoshada");
            doctorDB.insert("vineel","doctor since 2010 ","Cardiologists","14","Yoshada");
        }
        return  view;
    }
}