package com.polytechnic.healthmanagement.Expenses.RecycleView;

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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.healthmanagement.Expenses.Fragment.meNewRecord;
import com.polytechnic.healthmanagement.Expenses.Fragment.medb;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.TrackYourHealth.Fragment.Ailment;
import com.polytechnic.healthmanagement.TrackYourHealth.Fragment.tyhDB;
import com.polytechnic.healthmanagement.TrackYourHealth.Model.TYHTable;
import com.polytechnic.healthmanagement.TrackYourHealth.RecycleView.AilmentsList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class meRecycleView extends RecyclerView.Adapter<meRecycleView.meViewHolder> {
    ArrayList<meNewRecord> mearr=new ArrayList<>();
    public meRecycleView(Context ct){
        load(ct);
    }
    public void load(Context ct){
        medb db=new medb(ct);
        mearr=db.readRecords();
        notifyDataSetChanged();
    }
    public void revload(Context ct){
        load(ct);
        Collections.reverse(mearr);
        notifyDataSetChanged();
    }
    class meViewHolder extends RecyclerView.ViewHolder{
        TextView title,amount,date;
        ImageView im;
        CardView cv;
        public meViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.me_records_title);
            amount=itemView.findViewById(R.id.me_records_amount);
            date=itemView.findViewById(R.id.me_records_date);
            im=itemView.findViewById(R.id.me_records_menu);
            cv=itemView.findViewById(R.id.me_records_cardView);
        }
    }
    @NonNull
    @Override
    public meViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf=LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.me_records_layout,parent,false);
        return new meViewHolder(v);
    }
    @Override
    public int getItemCount() {
        return mearr.size();
    }

    @Override
    public void onBindViewHolder(@NonNull meViewHolder holder, int position) {
        holder.title.setText((mearr.get(position)).title);
        mearr.get(position).tt();
//        int amt=(mearr.get(position).docfee+mearr.get(position).other+mearr.get(position).medexp+mearr.get(position).trans);
        holder.amount.setText(""+mearr.get(position).tamt);
        holder.date.setText(mearr.get(position).date);
        holder.im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medb db=new medb(v.getContext());
                int Id=db.getMeId(position);
                showAilmentMenu(v,Id,mearr.get(position));
            }
        });
        holder.cv.setOnClickListener(new View.OnClickListener() {
            TextView tv1,tv2,tv3,tv4,tv5,tv6;
            ImageView close;
            @Override
            public void onClick(View v) {
                Dialog see=new Dialog(v.getContext(),R.style.Dialogbox_border);
                see.setContentView(R.layout.me_desc_dialog);
                tv1=see.findViewById(R.id.me_desc_dialog_tv1);
                tv2=see.findViewById(R.id.me_desc_dialog_tv2);
                tv3=see.findViewById(R.id.me_desc_dialog_tv3);
                tv4=see.findViewById(R.id.me_desc_dialog_tv4);
                tv5=see.findViewById(R.id.me_desc_dialog_tv5);
                tv6=see.findViewById(R.id.me_desc_dialog_tv6);
                close=see.findViewById(R.id.me_desc_dialog_close);
                mearr.get(position).tt();
                tv1.setText((mearr.get(position).title));
                tv2.setText(String.valueOf((mearr.get(position).docfee)));
                tv3.setText(String.valueOf((mearr.get(position).trans)));
                tv4.setText(String.valueOf((mearr.get(position).medexp)));
                tv5.setText(String.valueOf((mearr.get(position).other)));
                tv6.setText(String.valueOf(mearr.get(position).tamt));
                see.show();
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        see.dismiss();
                    }
                });

            }
        });
    }


    public void showAilmentMenu(View v,  int Id, meNewRecord menew){
        PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
        popupMenu.getMenuInflater().inflate(R.menu.tyh_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            EditText Title,medexp,docfee,others,trans;
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                CharSequence title = item.getTitle();
                if (title.equals("Edit")) {
                    Dialog editMeRecord = new Dialog(v.getContext(),R.style.Dialogbox_border);
                    editMeRecord.setCancelable(false);
                    editMeRecord.setContentView(R.layout.me_new_record_dialog);
                    Button mecancel = editMeRecord.findViewById(R.id.me_dialog_cancel);
                    Button mesave = editMeRecord.findViewById(R.id.me_dialog_save);
                    Title=editMeRecord.findViewById(R.id.me_dialog_title);
                    medexp=editMeRecord.findViewById(R.id.me_dialog_medcost);
                    docfee=editMeRecord.findViewById(R.id.me_dialog_docfee);
                    trans=editMeRecord.findViewById(R.id.me_dialog_trans);
                    others=editMeRecord.findViewById(R.id.me_dialog_others);
                    Title.setText(menew.title);
                    medexp.setText(String.valueOf(menew.medexp));
                    docfee.setText(String.valueOf(menew.docfee));
                    trans.setText(String.valueOf(menew.trans));
                    others.setText(String.valueOf(menew.other));
                    editMeRecord.show();
                    mecancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editMeRecord.dismiss();
                            Toast.makeText(v.getContext(), "Edit Cancled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mesave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            meNewRecord mnr=new meNewRecord();
                            if(Title.getText().toString().trim().equals("")){
                                editMeRecord.dismiss();
                                Toast.makeText(v.getContext(), "Title can't be empty", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                mnr.title=Title.getText().toString().trim();
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
                                if(others.getText().toString().trim().equals(""))
                                    mnr.other=0;
                                else
                                    mnr.other=Integer.parseInt(String.valueOf(others.getText()));
                                Date d1 = new Date();
                                mnr.date = new SimpleDateFormat("yyyy-MM-dd").format(d1);
                                medb db = new medb(v.getContext());
                                db.updateMeRecord(mnr, Id);
                                editMeRecord.dismiss();
                                load(v.getContext());
                            }
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
                            medb db=new medb(v.getContext());
                            db.deleteMeRecord(Id);
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
                    return true;
                }
                return true;
            }
        });
        popupMenu.show();
    }
    public void filterList(ArrayList<meNewRecord> mes){
        mearr=mes;
        notifyDataSetChanged();
    }
    public void filter(String text){
        ArrayList<meNewRecord> newmes=new ArrayList<>();
        for (meNewRecord menr:mearr){
            menr.tt();
            if(menr.date.toLowerCase().contains(text.toLowerCase().trim())){
                newmes.add(menr);
            } else if (menr.title.toLowerCase().toString().contains(text.toLowerCase())) {
                newmes.add(menr);
            }else if (String.valueOf(menr.docfee).toLowerCase().contains(text.toLowerCase())) {
                newmes.add(menr);
            }else if (String.valueOf(menr.trans).toLowerCase().contains(text.toLowerCase())) {
                newmes.add(menr);
            }else if (String.valueOf(menr.medexp).toLowerCase().contains(text.toLowerCase())) {
                newmes.add(menr);
            }else if (String.valueOf(menr.other).toLowerCase().contains(text.toLowerCase())) {
                newmes.add(menr);
            }else if (String.valueOf(menr.tamt).toLowerCase().contains(text.toLowerCase())) {
                newmes.add(menr);
            }
        }
        filterList(newmes);
    }
}
