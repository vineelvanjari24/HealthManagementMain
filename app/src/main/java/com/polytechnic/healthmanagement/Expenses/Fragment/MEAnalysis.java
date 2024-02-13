package com.polytechnic.healthmanagement.Expenses.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.polytechnic.healthmanagement.R;

import java.util.ArrayList;

public class MEAnalysis extends AppCompatActivity {
    ArrayList<meNewRecord> mearr=new ArrayList<>();
    TextView docfee,medexp,trans,others,total;
    LinearLayout display;
    EditText year,month;
    Button analyze;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meanalysis);

        display=findViewById(R.id.me_analysis_display);
        display.setVisibility(View.GONE);

        year=findViewById(R.id.me_analysis_year);
        month=findViewById(R.id.me_analysis_month);
        docfee=findViewById(R.id.me_analysis_docfee);
        medexp=findViewById(R.id.me_analysis_medexp);
        trans=findViewById(R.id.me_analysis_trans);
        others=findViewById(R.id.me_analysis_others);
        total=findViewById(R.id.me_analysis_Total);
        analyze=findViewById(R.id.me_analyze);

        medb db=new medb(getApplicationContext());

        month.setInputType(InputType.TYPE_CLASS_NUMBER);
        DigitsKeyListener keyListener = DigitsKeyListener.getInstance("0123456789");
        month.setKeyListener(keyListener);

        final int minValue = 1;
        final int maxValue = 12;
        InputFilter inputFilter = (source, start, end, dest, dstart, dend) -> {
            try {
                String inputText = dest.toString().substring(0, dstart) +
                        source.toString().substring(start, end) +
                        dest.toString().substring(dend);

                int input = Integer.parseInt(inputText);
                if (input >= minValue && input <= maxValue) {
                    return null; // Accept the input
                } else {
                    return ""; // Reject the input
                }
            } catch (NumberFormatException e) {
                return ""; // Reject non-numeric input
            }
        };

        month.setFilters(new InputFilter[]{inputFilter});

        analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(year.getText().toString().trim().isEmpty() || month.getText().toString().trim().isEmpty()){
                    Toast.makeText(MEAnalysis.this, "Plaese enter year and month ", Toast.LENGTH_SHORT).show();
                }
                else{
                    String y=String.valueOf(year.getText()).trim();
                    String m=String.valueOf(month.getText()).trim();
                    String searchString="";
                    if(m.length()==1){
                        searchString=y+"-0"+m;
                    }else {
                        searchString=y+"-"+m;
                    }
                    mearr=db.analyzeRecords(searchString);

                    int df=0;
                    int medcost=0;
                    int oth=0;
                    int transcost=0;
                    int totalcost=0;

                    for (int i=0;i<mearr.size();i++){
                        df+=mearr.get(i).docfee;
                        medcost+=mearr.get(i).medexp;
                        transcost+=mearr.get(i).trans;
                        oth+=mearr.get(i).other;
                        mearr.get(i).tt();
                        totalcost+=mearr.get(i).tamt;
                    }

                    docfee.setText(String.valueOf(df));
                    medexp.setText(String.valueOf(medcost));
                    trans.setText(String.valueOf(transcost));
                    others.setText(String.valueOf(oth));
                    total.setText(String.valueOf(totalcost));

                    display.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}