package com.polytechnic.healthmanagement.TrackYourHealth.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
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
    AilmentsList a;
    FloatingActionButton fbtn;
    EditText ailmentname,p1,p2;
    Button dcancel,dsave;
    ArrayList<TYHTable> tablearr;
    EditText search;
    ImageView sort_ailments;
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
        search=View.findViewById(R.id.tyh_search);
        sort_ailments=View.findViewById(R.id.tyh_sort);
        sort_ailments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                showsortMenu(v);
            }
        });

        Dialog newAilment=new Dialog(View.getContext());
        newAilment.setCancelable(false);
        newAilment.setContentView(R.layout.tyh_main_dialogbox);
        dcancel=newAilment.findViewById(R.id.tyh_main_dialog_cancel);
        dsave=newAilment.findViewById(R.id.tyh_main_dialog_save);
        a=new AilmentsList(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(a);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {a.filter(s.toString());
            }
        });
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    search.clearFocus();
                    search.setText("");
                    a.load(getContext());
                    return true;
                }
                return false;
            }
        });
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
                tyhDB db = new tyhDB(getContext());
                ailmentname=newAilment.findViewById(R.id.tyh_main_title);
                p1=newAilment.findViewById(R.id.tyh_main_p1);
                p2=newAilment.findViewById(R.id.tyh_main_p2);
                TYHTable t=new TYHTable();
                t.Name=ailmentname.getText().toString().trim().replaceAll(" ","_");
                if(ailmentname.getText().toString().trim().equals("")){
                    newAilment.dismiss();
                    Toast.makeText(getContext(), "Ailment Name is Must", Toast.LENGTH_SHORT).show();
                }  else if (!containsSpecialCharacters(t.Name)) {
                    newAilment.dismiss();
                    Toast.makeText(v.getContext(), "Table Name should contain a-z or  0-9  or _  or $ ", Toast.LENGTH_SHORT).show();
                } else if (Character.isDigit(t.Name.charAt(0))) {
                    newAilment.dismiss();
                    Toast.makeText(v.getContext(), "Table Name should not start with Number", Toast.LENGTH_SHORT).show();
                } else if(db.tableExistsOrNot(t.Name)){
                    newAilment.dismiss();
                    Toast.makeText(v.getContext(), "Table Aldready Exists", Toast.LENGTH_SHORT).show();
                } else if (!containsSpecialCharacters(p1.getText().toString().trim()) ||!containsSpecialCharacters(p2.getText().toString().trim()) ){
                    newAilment.dismiss();
                    Toast.makeText(v.getContext(), "Parameter Name should contain only \n a-z , 0-9 , _ , $", Toast.LENGTH_SHORT).show();
                } else if (!p1.getText().toString().isEmpty() && Character.isDigit(p1.getText().toString().trim().charAt(0))){
                    newAilment.dismiss();
                    Toast.makeText(v.getContext(), "Parameter Name should not start with numbers", Toast.LENGTH_SHORT).show();
                }  else if (!p2.getText().toString().isEmpty() && Character.isDigit(p2.getText().toString().trim().charAt(0))){
                    newAilment.dismiss();
                    Toast.makeText(v.getContext(), "Parameter Name should not start with numbers", Toast.LENGTH_SHORT).show();
                }else if (p2.getText().toString().trim().equals(p1.getText().toString().trim())){
                    newAilment.dismiss();
                    Toast.makeText(v.getContext(), "Parameters should be different", Toast.LENGTH_SHORT).show();
                }
                else {
                    t.P1 = p1.getText().toString().trim().replaceAll(" ", "_");
                    t.P2 = p2.getText().toString().trim().replaceAll(" ", "_");
                    newAilment.dismiss();
                    db.addAilment(t);
                    db.addTable(t);
                    Toast.makeText(getContext(), "Ailment Saved", Toast.LENGTH_SHORT).show();
                    a.load(getContext());
                }
                ailmentname.setText("");
                p1.setText("");
                p1.clearFocus();
                p2.setText("");
                p2.clearFocus();
            }
        });
        return View;
    }
    public boolean containsSpecialCharacters(String input) {
        String regex = "^[a-zA-Z0-9_$]*$";
        return input.matches(regex);
    }
    public void showsortMenu(View v){
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.sort_items, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                CharSequence title = item.getTitle();
                if (title.equals("New To Old")){
                    a.load(v.getContext());
                    return true;
                } else if (title.equals("Old To New")) {
                    a.revload(v.getContext());
                    return true;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}