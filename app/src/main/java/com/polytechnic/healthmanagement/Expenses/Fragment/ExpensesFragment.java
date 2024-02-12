package com.polytechnic.healthmanagement.Expenses.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.polytechnic.healthmanagement.Expenses.RecycleView.meRecycleView;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.TrackYourHealth.RecycleView.ParticularAilment;


public class ExpensesFragment extends Fragment {
    RecyclerView rv;
    FloatingActionButton fbtn;
    Button mecancel,mesave;
    EditText title,medexp,other,docfee,trans;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_expenses, container, false);

        rv=v.findViewById(R.id.merecyclv);
        fbtn=v.findViewById(R.id.mefbt);
        Dialog newme=new Dialog(v.getContext(),R.style.Dialogbox_border);
        newme.setContentView(R.layout.me_new_record_dialog);
        newme.setCancelable(false);
        mecancel=newme.findViewById(R.id.me_dialog_cancel);
        mesave=newme.findViewById(R.id.me_dialog_save);
        other=newme.findViewById(R.id.me_dialog_others);
        title=newme.findViewById(R.id.me_dialog_title);
        medexp=newme.findViewById(R.id.me_dialog_medcost);
        docfee=newme.findViewById(R.id.me_dialog_docfee);
        trans=newme.findViewById(R.id.me_dialog_trans);
        meRecycleView ma=new meRecycleView(v.getContext());
        rv.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(ma);

        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newme.show();
            }
        });
        mecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newme.dismiss();
                other.setText("");
                title.setText("");
                medexp.setText("");
                docfee.setText("");
                trans.setText("");
                trans.clearFocus();
                Toast.makeText(v.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        mesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meNewRecord nr=new meNewRecord();
                if(title.getText().toString().trim().equals("")){
                    newme.dismiss();
                    other.setText("");
                    title.setText("");
                    medexp.setText("");
                    docfee.setText("");
                    trans.setText("");
                    trans.clearFocus();
                    Toast.makeText(v.getContext(), "Please enter valid details", Toast.LENGTH_SHORT).show();
                }
                else{
                        meNewRecord mnr=new meNewRecord();
                        if(docfee.getText().toString().trim().equals(""))
                            mnr.docfee=0;
                        else
                            mnr.docfee=Integer.parseInt(String.valueOf(docfee.getText()));
                        if(trans.getText().toString().trim().equals(""))
                            mnr.trans=0;
                        else
                            mnr.trans=Integer.parseInt(String.valueOf(trans.getText()));
                        if(medexp.getText().toString().trim().equals(""))
                            mnr.medexp=0;
                        else
                            mnr.medexp=Integer.parseInt(String.valueOf(medexp.getText()));
                        if(other.getText().toString().trim().equals(""))
                            mnr.other=0;
                        else
                            mnr.other=Integer.parseInt(String.valueOf(other.getText()));
                        mnr.title=title.getText().toString().trim();
                        medb db=new medb(v.getContext());
                        db.addRecord(mnr);
                        Toast.makeText(v.getContext(), "Record Added", Toast.LENGTH_SHORT).show();
                        newme.dismiss();
                        ma.load(v.getContext());
                }
            }
        });
        return v;
    }
}


