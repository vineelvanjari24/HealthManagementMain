package com.polytechnic.healthmanagement.TrackYourHealth.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.TrackYourHealth.Model.TYHTable;
import com.polytechnic.healthmanagement.TrackYourHealth.RecycleView.AilmentsList;

import java.util.ArrayList;

import kotlin.jvm.internal.FloatSpreadBuilder;

public class TrackYourHealthFragment extends Fragment {

    RecyclerView rv;
    FloatingActionButton fbtn;
    EditText ailmentname,p1,p2;
    Button dcancel,dsave;
    ArrayList<TYHTable> tablearr;
    public TrackYourHealthFragment() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View View= inflater.inflate(R.layout.fragment_track_your_health, container, false);
        rv=View.findViewById(R.id.tyh_main_rv);
        fbtn=View.findViewById(R.id.tyh_main_floatbtn);
        Dialog newAilment=new Dialog(View.getContext());
        newAilment.setCancelable(false);
        newAilment.setContentView(R.layout.tyh_main_dialogbox);
        dcancel=newAilment.findViewById(R.id.tyh_main_dialog_cancel);
        dsave=newAilment.findViewById(R.id.tyh_main_dialog_save);
        AilmentsList a=new AilmentsList(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(a);
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                newAilment.show();
            }
        });
        dcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                newAilment.dismiss();
            }
        });
        dsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                newAilment.dismiss();
                ailmentname=newAilment.findViewById(R.id.tyh_main_title);
                p1=newAilment.findViewById(R.id.tyh_main_p1);
                p2=newAilment.findViewById(R.id.tyh_main_p2);
                TYHTable t=new TYHTable();
                t.Name=ailmentname.getText().toString();
                ailmentname.setText("");
                t.P1=p1.getText().toString();
                p1.setText("");
                t.P2=p2.getText().toString();
                p2.setText("");
                p2.clearFocus();
                tyhDB db=new tyhDB(getContext());
                db.addAilment(t);
                db.addTable(t);
                Toast.makeText(getContext(), "Ailment Saved", Toast.LENGTH_SHORT).show();
                a.load(getContext());
            }
        });
        return View;
    }
}