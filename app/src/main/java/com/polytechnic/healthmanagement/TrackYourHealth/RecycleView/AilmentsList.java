package com.polytechnic.healthmanagement.TrackYourHealth.RecycleView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.TrackYourHealth.Fragment.Ailment;
import com.polytechnic.healthmanagement.TrackYourHealth.Fragment.tyhDB;
import com.polytechnic.healthmanagement.TrackYourHealth.Model.TYHTable;

import java.util.ArrayList;
import java.util.Collections;

public class AilmentsList extends RecyclerView.Adapter<AilmentsList.AilmentsViewHolder> {
    ArrayList<TYHTable> tables=new ArrayList<>();
    Context ct;
    public AilmentsList(Context ct )
    {
        this.ct=ct;
        load(ct);
    }
    public void load(Context ct){
        tyhDB db=new tyhDB(ct);
        tables=db.readAilments();
        notifyDataSetChanged();
    }
    public void revload(Context ct){
        load(ct);
        Collections.reverse(tables);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AilmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf=LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.ailments_list_layout,parent,false);
        return new AilmentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AilmentsViewHolder holder, int position) {
        holder.tv.setText(tables.get(position).Name.replaceAll("_"," "));
        holder.im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v,tables.get(position));
            }
        });
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ct.getApplicationContext(), Ailment.class);
                intent.putExtra("TableName",tables.get(position).Name);
                intent.putExtra("P1",tables.get(position).P1);
                intent.putExtra("P2",tables.get(position).P2);
                ct.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    class AilmentsViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        CardView cv;
        ImageView im;
        public AilmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tyh_ailmentdisplay);
            cv=itemView.findViewById(R.id.TYH_Ailmentlist_cv);
            im=itemView.findViewById(R.id.tyh_menu_img);
        }
    }

    public void showMenu(View v,TYHTable tb){
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.tyh_options, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                CharSequence title = item.getTitle();
                if (title.equals("Edit")){
                    Dialog editAilment=new Dialog(v.getContext(),R.style.Dialogbox_border);
                    editAilment.setCancelable(false);
                    editAilment.setContentView(R.layout.tyh_main_dialogbox);
                    Button dcancel=editAilment.findViewById(R.id.tyh_main_dialog_cancel);
                    Button dsave=editAilment.findViewById(R.id.tyh_main_dialog_save);
                    EditText ailmentname=editAilment.findViewById(R.id.tyh_main_title);
                    EditText p1=editAilment.findViewById(R.id.tyh_main_p1);
                    EditText p2=editAilment.findViewById(R.id.tyh_main_p2);
                    TextView tv1=editAilment.findViewById(R.id.tyh_main_dialog_text1);
                    TextView tv2=editAilment.findViewById(R.id.tyh_main_dialog_text2);
                    ailmentname.setText(tb.Name.replaceAll("_"," "));
                    p1.setText(tb.P1.replaceAll("_"," "));
                    p2.setText(tb.P2.replaceAll("_"," "));
//                    if(tb.P1.equals("ParameterOne") || tb.P1.equals("ParameterTwo")) {
//                        p1.setVisibility(View.GONE);
//                        tv1.setVisibility(View.GONE);
//                    }
//                    if(tb.P2.equals("ParameterOne") || tb.P2.equals("ParameterTwo")) {
//                        p2.setVisibility(View.GONE);
//                        tv2.setVisibility(View.GONE);
//                    }
                    editAilment.show();
                    dcancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editAilment.dismiss();
                        }
                    });

                    dsave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TYHTable th=new TYHTable();
                            tyhDB db = new tyhDB(v.getContext());
                            String tname=ailmentname.getText().toString().replaceAll(" ","_");
                            Boolean te=db.tableExistsOrNot(tname);
                            if(ailmentname.getText().toString().trim().equals("")) {
                                editAilment.dismiss();
                                Toast.makeText(ct.getApplicationContext(), "Enter a valid Ailment Title", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                th.Name = ailmentname.getText().toString().replaceAll(" ", "_");
                                if (p1.getText().toString().trim().equals(""))
                                    th.P1 = "Invalid";
                                else
                                    th.P1 = p1.getText().toString().trim().replaceAll(" ", "_");
                                if (p2.getText().toString().trim().equals(""))
                                    th.P2 = "Invalid";
                                else
                                    th.P2 = p2.getText().toString().trim().replaceAll(" ", "_");
                                db.updateTables(th, tb.Name);
                                editAilment.dismiss();
                                load(v.getContext());
                            }
                        }
                    });

                        return true;
                } else if (title.equals("Delete")) {

                    AlertDialog.Builder confirm=new AlertDialog.Builder(ct);
                    confirm.setTitle("Delete Confirmation");
                    confirm.setIcon(R.drawable.delete);
                    confirm.setMessage("Do you want to delete for sure?");
                    confirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                             tyhDB db=new tyhDB(v.getContext());
                             db.deleteAilment(tb);
                             load(v.getContext());

                        }
                    });
                    confirm.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(v.getContext(), "Delete Canceled",Toast.LENGTH_SHORT).show();
                        }
                    });
                    confirm.show();
                }
                    return true;
            }
        });
        popupMenu.show();
    }
    public void filterList(ArrayList<TYHTable> arrs){
        tables=arrs;
        notifyDataSetChanged();
    }
    public void filter(String text){
        ArrayList<TYHTable> newta=new ArrayList<>();
        for (TYHTable t:tables){
            if(t.Name.toLowerCase().contains(text.toLowerCase().trim())){
                newta.add(t);
            } else if (t.P1.toString().contains(text.toLowerCase())) {
                newta.add(t);
            }else if (t.P1.toString().contains(text.toLowerCase())) {
                newta.add(t);
            }
        }
        filterList(newta);
    }
}
