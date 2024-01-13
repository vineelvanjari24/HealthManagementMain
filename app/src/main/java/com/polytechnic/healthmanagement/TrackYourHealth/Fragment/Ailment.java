package com.polytechnic.healthmanagement.TrackYourHealth.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
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

public class Ailment extends AppCompatActivity {

    EditText v1,v2;
    RecyclerView rv;
    FloatingActionButton fbtn;
    Button dsave,dcancel;
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



        fbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                TextView tv1,tv2;
                Dialog addAilmentRecord=new Dialog(v.getContext());
                addAilmentRecord.setContentView(R.layout.tyh_addailment_dialog);
                addAilmentRecord.setCancelable(false);
                tv1=addAilmentRecord.findViewById(R.id.tyh_ailment_text1);
                tv2=addAilmentRecord.findViewById(R.id.tyh_ailment_text2);
                tv1.setText("ABCD");
                tv2.setText("EFGH");
                v1=addAilmentRecord.findViewById(R.id.tyh_Ailment_value1);
                v2=addAilmentRecord.findViewById(R.id.tyh_Ailment_value2);
                addAilmentRecord.setContentView(R.layout.tyh_addailment_dialog);
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
                        data.P1=v1.getText().toString();
                        Log.d("p1",data.P1);
                        data.P2=String.valueOf(v2.getText());
                        Log.d("p1",data.P2);
                        addAilmentRecord.dismiss();
                        tyhDB db=new tyhDB(getApplicationContext());
                        db.addAilmentRecord(tb,data);
                        Toast.makeText(Ailment.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
