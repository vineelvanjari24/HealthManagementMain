package com.polytechnic.healthmanagement.Expenses.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.polytechnic.healthmanagement.Expenses.RecycleView.meRecycleView;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.TrackYourHealth.Fragment.tyhDB;
import com.polytechnic.healthmanagement.TrackYourHealth.Model.TYHTable;
import com.polytechnic.healthmanagement.TrackYourHealth.RecycleView.ParticularAilment;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ExpensesFragment extends Fragment {
    RecyclerView rv;
    FloatingActionButton fbtn;
    Button mecancel,mesave,analysis;
    EditText title,medexp,other,docfee,trans,search;
    ImageView sort;
    meRecycleView ma;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_expenses, container, false);

        rv=v.findViewById(R.id.merecyclv);
        search=v.findViewById(R.id.me_search);
        analysis=v.findViewById(R.id.me_analysis);
        fbtn=v.findViewById(R.id.mefbt);
        sort=v.findViewById(R.id.me_sort);
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
        ma=new meRecycleView(v.getContext());
        rv.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(ma);
        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MEAnalysis.class);
                startActivity(intent);
            }
        });
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showsortMenu(v);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {ma.filter(s.toString());
            }
        });
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == android.view.KeyEvent.KEYCODE_BACK && event.getAction() == android.view.KeyEvent.ACTION_UP) {
                    search.clearFocus();
                    search.setText("");
                    ma.load(getContext());
                    return true;
                }
                return false;
            }
        });

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
                        Date d1 = new Date();
                        mnr.date = new SimpleDateFormat("yyyy-MM-dd").format(d1);
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
    public void showsortMenu(View v){
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.sort_items, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                CharSequence title = item.getTitle();
                if (title.equals("New To Old")){
                    ma.load(v.getContext());
                    return true;
                } else if (title.equals("Old To New")) {
                    ma.revload(v.getContext());
                    return true;
                }
                return true;
            }
        });
        popupMenu.show();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Assuming you have a reference to the hosting Activity
        if (getActivity() != null) {
            // Set the new title to the Toolbar in the hosting Activity
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Expenses");
        }
    }
}


