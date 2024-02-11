package com.polytechnic.healthmanagement.TrackYourHealth.RecycleView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.TrackYourHealth.Fragment.Ailment;
import com.polytechnic.healthmanagement.TrackYourHealth.Fragment.tyhDB;
import com.polytechnic.healthmanagement.TrackYourHealth.Model.TYHTable;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ParticularAilment extends RecyclerView.Adapter<ParticularAilment.ParticularAilmentViewHolder> {
    ArrayList<TYHTable> table=new ArrayList<>();
    TYHTable tb;
    Context ct;
    public ParticularAilment(Context ct,TYHTable tt)
    {
        load(ct,tt);
    }

    public void load(Context ct,TYHTable tb){
        tyhDB db=new tyhDB(ct);
        this.tb=tb;
        table=db.readParticularAilment(tb.Name);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ParticularAilmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf=LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.tyh_particular_ailment,parent,false);
        return new ParticularAilmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticularAilmentViewHolder holder, int position) {
        Date d1 = new Date();
        String cusmdate = new SimpleDateFormat("YYYY-MM-DD").format(d1);
        holder.im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tyhDB db=new tyhDB(v.getContext());
                int Id=db.getRecordId(tb,position);
                showAilmentMenu(v,tb,Id,table.get(position));
            }
        });
        holder.date.setText(cusmdate);
        if (tb.P1.equals("ParameterOne") || tb.P1.equals("ParameterTwo") || tb.P1.equals("Invalid") || table.get(position).P1.equals("notInserted")) {
            holder.tv1.setVisibility(View.GONE);
            holder.tv2.setVisibility(View.GONE);
        }
        else {
            holder.tv1.setText(tb.P1.replaceAll("_"," "));
            holder.tv2.setText(table.get(position).P1);
        }
        if (tb.P2.equals("ParameterOne") || tb.P2.equals("ParameterTwo") || tb.P2.equals("Invalid") ||table.get(position).P2.equals("notInserted")) {
            holder.tv3.setVisibility(View.GONE);
            holder.tv4.setVisibility(View.GONE);
        }
        else {
            holder.tv3.setText(tb.P2.replaceAll("_"," "));
            holder.tv4.setText(table.get(position).P2);
        }

    }
    @Override
    public int getItemCount(){
            return table.size();
        }
    class ParticularAilmentViewHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2,tv3,tv4,date;
        ImageView im;
        public ParticularAilmentViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.tyh_ailment_date);
            tv1=itemView.findViewById(R.id.tyh_pa_t1);
            tv2=itemView.findViewById(R.id.tyh_pa_t2);
            tv3=itemView.findViewById(R.id.tyh_pa_t3);
            tv4=itemView.findViewById(R.id.tyh_pa_t4);
            im=itemView.findViewById(R.id.tyh_ailment_menu);
        }
    }
    public void showAilmentMenu(View v,TYHTable tb,int Id,TYHTable tv){
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.tyh_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                CharSequence title = item.getTitle();
                if (title.equals("Edit")) {
                    Dialog editAilmentRecord = new Dialog(v.getContext());
                    editAilmentRecord.setCancelable(false);
                    editAilmentRecord.setContentView(R.layout.tyh_addailment_dialog);
                    Button dcancel = editAilmentRecord.findViewById(R.id.tyh_Ailment_Dialog_cancel);
                    Button dsave = editAilmentRecord.findViewById(R.id.tyh_Ailment_Dialog_save);
                    TextView p1 = editAilmentRecord.findViewById(R.id.tyh_ailment_text1);
                    TextView p2 = editAilmentRecord.findViewById(R.id.tyh_ailment_text2);
                    EditText e1 = editAilmentRecord.findViewById(R.id.tyh_Ailment_value1);
                    EditText e2 = editAilmentRecord.findViewById(R.id.tyh_Ailment_value2);
                    p1.setText(tb.P1.replaceAll("_"," "));
                    p2.setText(tb.P2.replaceAll("_"," "));
                    e1.setText(tv.P1);
                    e2.setText(tv.P2);
                    if (tb.P1.equals("ParameterOne") || tb.P1.equals("ParameterTwo")) {
//                        p1.setVisibility(View.INVISIBLE);
//                        e1.setVisibility(View.INVISIBLE);
                        p1.setVisibility(View.GONE);
                        e1.setVisibility(View.GONE);
                    }
                    if (tb.P2.equals("ParameterOne") || tb.P2.equals("ParameterTwo")){
//                        p2.setVisibility(View.INVISIBLE);
//                        e2.setVisibility(View.INVISIBLE);
                        p2.setVisibility(View.GONE);
                        e2.setVisibility(View.GONE);

                    }
                    editAilmentRecord.show();
                    dcancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editAilmentRecord.dismiss();
                            Toast.makeText(v.getContext(), "Edit Cancled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dsave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TYHTable th=new TYHTable();
                            if(e1.getText().toString().trim().equals(""))
                                th.P1="Invalid";
                            else
                                th.P1=e1.getText().toString();
                            if(e2.getText().toString().trim().equals(""))
                                th.P2="Invalid";
                            else
                                th.P2=e2.getText().toString();
                            tyhDB db=new tyhDB(v.getContext());
                            db.updateAilmentRecord(tb,th,Id);
                            editAilmentRecord.dismiss();
                            load(v.getContext(),tb);
                        }
                    });

                    return true;
                }else if (title.equals("Delete")) {
                    AlertDialog.Builder confirm=new AlertDialog.Builder(v.getContext());
                    confirm.setTitle("Delete Confirmation");
                    confirm.setMessage("Do you want to delete for sure?");
                    confirm.setCancelable(false);
                    confirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tyhDB db=new tyhDB(v.getContext());
                            db.deleteAilmentRecord(tb,Id);
                            load(v.getContext(),tb);
                        }
                    });
                    confirm.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(v.getContext(), "Delete Canceled",Toast.LENGTH_SHORT).show();
                        }
                    });
                    confirm.show();
                    return true;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}
