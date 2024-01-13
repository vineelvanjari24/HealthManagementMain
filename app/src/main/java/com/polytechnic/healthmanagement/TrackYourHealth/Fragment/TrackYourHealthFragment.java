package com.polytechnic.healthmanagement.TrackYourHealth.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.polytechnic.healthmanagement.R;


public class TrackYourHealthFragment extends Fragment {

    public TrackYourHealthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View View= inflater.inflate(R.layout.fragment_track_your_health, container, false);

        return View;
    }
}