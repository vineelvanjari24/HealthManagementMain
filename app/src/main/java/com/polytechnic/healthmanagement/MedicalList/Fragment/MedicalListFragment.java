package com.polytechnic.healthmanagement.MedicalList.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.polytechnic.healthmanagement.R;



public class MedicalListFragment extends Fragment {



    public MedicalListFragment() {
        // Required empty public constructor
    }
    public MedicalListFragment(Context context) {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medical_list, container, false);
    }
}