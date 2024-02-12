package com.polytechnic.healthmanagement.TrackYourHealth.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.TrackYourHealth.Model.TYHTable;
import com.polytechnic.healthmanagement.TrackYourHealth.RecycleView.AilmentsList;
import com.polytechnic.healthmanagement.TrackYourHealth.RecycleView.ParticularAilment;

import java.util.ArrayList;

public class Ailment extends AppCompatActivity {
    TextView tv1,tv2;
    EditText v1,v2;
    RecyclerView rv;
    FloatingActionButton fbtn;
    Button dsave,dcancel;
    Context ac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailment);

        TYHTable tb=new TYHTable();
        Bundle b=getIntent().getExtras();
        tb.Name=b.getString("TableName");
        tb.P1=b.getString("P1");
        tb.P2=b.getString("P2");
        rv=findViewById(R.id.tyh_Ailment_recycleView);
        fbtn=findViewById(R.id.tyh_Ailment_addbtn);
        ac=getApplicationContext();
        ParticularAilment pa=new ParticularAilment(ac,tb);
        rv.setLayoutManager(new LinearLayoutManager(ac, LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(pa);

        fbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                Dialog addAilmentRecord=new Dialog(v.getContext(),R.style.Dialogbox_border);
                addAilmentRecord.setContentView(R.layout.tyh_addailment_dialog);
                addAilmentRecord.setCancelable(false);
                tv1=addAilmentRecord.findViewById(R.id.tyh_ailment_text1);
                tv2=addAilmentRecord.findViewById(R.id.tyh_ailment_text2);
                tv1.setText(tb.P1.replaceAll("_"," "));
                tv2.setText(tb.P2.replaceAll("_"," "));
                v1=addAilmentRecord.findViewById(R.id.tyh_Ailment_value1);
                v2=addAilmentRecord.findViewById(R.id.tyh_Ailment_value2);
                if (tb.P1.equals("ParameterOne") || tb.P1.equals("ParameterTwo")){
                    v1.setVisibility(View.GONE);
                    tv1.setVisibility(View.GONE);
                }
                if (tb.P2.equals("ParameterOne") || tb.P2.equals("ParameterTwo")){
                    v2.setVisibility(View.GONE);
                    tv2.setVisibility(View.GONE);
                }
                dcancel=addAilmentRecord.findViewById(R.id.tyh_Ailment_Dialog_cancel);
                dsave=addAilmentRecord.findViewById(R.id.tyh_Ailment_Dialog_save);
                addAilmentRecord.show();

                dcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        addAilmentRecord.dismiss();
                    }
                });
                dsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v){
                        TYHTable data=new TYHTable();
                        if (tb.P1.equals("ParameterOne") || tb.P1.equals("ParameterTwo"))
                                data.P1="InValid";
                            else
                            data.P1=v1.getText().toString();

                        if (tb.P2.equals("ParameterOne") || tb.P2.equals("ParameterTwo"))
                                data.P2="InValid";
                            else
                            data.P2=v2.getText().toString();
                        addAilmentRecord.dismiss();
                        tyhDB db=new tyhDB(getApplicationContext());
                        db.addAilmentRecord(tb,data);
                        Toast.makeText(Ailment.this, "Saved", Toast.LENGTH_SHORT).show();
                        pa.load(ac,tb);
                    }
                });
            }
        });
    }

}
