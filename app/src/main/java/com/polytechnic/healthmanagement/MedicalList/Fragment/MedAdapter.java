package com.polytechnic.healthmanagement.MedicalList.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.healthmanagement.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.MedViewHolder> {
    Context ct;
    ArrayList<Medicine> mlistarr=new ArrayList<>();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public MedAdapter(Context c, ArrayList<Medicine> am){
        ct=c;
        mlistarr=am;
    }
    @NonNull
    @Override
    public MedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MedViewHolder holder, int position) {
        holder.tvcn.setText(mlistarr.get(position).cn);
        holder.tvcf.setText(mlistarr.get(position).cf);
        holder.tvmed.setText(mlistarr.get(position).med);
        holder.tbl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showMenu(v,mlistarr.get(position));
                return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return mlistarr.size();
    }
    public class MedViewHolder extends RecyclerView.ViewHolder{
        TextView tvcn,tvcf,tvmed;
        TableLayout tbl;
        public MedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvcn=itemView.findViewById(R.id.mlcn);
            tvcf=itemView.findViewById(R.id.mlcf);
            tvmed=itemView.findViewById(R.id.mlmed);
            tbl=itemView.findViewById(R.id.mltbrow);
        }
    }
    public void showMenu(View v,Medicine med){
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.tyh_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                CharSequence title = item.getTitle();
                if (title.equals("Edit")){
                    Dialog editmed=new Dialog(v.getContext());
                    editmed.setContentView(R.layout.medicine_edit_dialog);
                    editmed.setCancelable(false);
                    EditText editcn,editcf,editm;
                    editm=editmed.findViewById(R.id.med_edit_dialog_medlist);
                    editcn=editmed.findViewById(R.id.med_edit_dialog_cn);
                    editcf=editmed.findViewById(R.id.med_edit_dialog_cf);
                    Button mededitsave=editmed.findViewById(R.id.med_edit_dialog_save);
                    Button mededitcancel=editmed.findViewById(R.id.med_edit_dialog_cancel);
                    editcn.setText(med.cn);
                    editm.setText(med.med);
                    editcf.setText(med.cf);
                    mededitcancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editmed.dismiss();
                            Toast.makeText(v.getContext(), "Editing Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mededitsave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Medicine mededit=new Medicine();
                            mededit.cn=editcn.getText().toString().trim();
                            mededit.cf=editcf.getText().toString().trim();
                            mededit.med=editm.getText().toString().trim();
                            editmed.dismiss();
                            db.collection("MedicalList").document(med.Id).set(mededit)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ct, "Edited Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ct, "Failed To Edit \n Try Again Later", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                    editmed.show();
                    return true;
                } else if (title.equals("Delete")) {
                    AlertDialog.Builder confirm=new AlertDialog.Builder(v.getContext());
                    confirm.setTitle("Delete Confirmation");
                    confirm.setMessage("Do you want to delete for sure?");
                    confirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.collection("MedicalList").document(med.Id).delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ct, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ct, "Failed to delete \n Try again later", Toast.LENGTH_SHORT).show();
                                        }
                                    });
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
