package com.polytechnic.healthmanagement.DoctorList.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.polytechnic.healthmanagement.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DocsRecycle extends RecyclerView.Adapter<DocsRecycle.DocsViewHolder> {
    public void load(){
        notifyDataSetChanged();
    }
    ArrayList<Doctor> d;
    Context ct;
    public DocsRecycle(ArrayList<Doctor> doc, Context c){
        d=doc;
        this.ct=c;
    }
    @NonNull
    @Override
    public DocsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.doclist,parent,false);
        return new DocsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DocsViewHolder holder, int position) {
        holder.name.setText(d.get(position).name);
        holder.spec.setText(d.get(position).spec);
        Picasso.get().load(d.get(position).imgUri).into(holder.img);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog docdet=new Dialog(v.getContext());
                docdet.setContentView(R.layout.doc_details);
                docdet.setCancelable(false);
                ImageView docimg=docdet.findViewById(R.id.doc_det_img);
                TextView name=docdet.findViewById(R.id.doc_det_name);
                TextView sp=docdet.findViewById(R.id.doc_det_spec);
                TextView wk=docdet.findViewById(R.id.doc_det_work);
                TextView ex=docdet.findViewById(R.id.doc_det_exp);
                TextView de=docdet.findViewById(R.id.doc_det_desc);
                ImageView close=docdet.findViewById(R.id.doc_det_close);
                Button del=docdet.findViewById(R.id.doc_det_delete);
                Button edit=docdet.findViewById(R.id.doc_det_edit);
                Picasso.get().load(d.get(position).imgUri).into(docimg);
                name.setText(d.get(position).name);
                sp.setText(d.get(position).spec);
                ex.setText(String.valueOf(d.get(position).exp));
                wk.setText(d.get(position).work);
                de.setText(d.get(position).desc);
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        docdet.dismiss();
                        AlertDialog.Builder cfmdel = new AlertDialog.Builder(v.getContext());
                        cfmdel.setTitle("Confirm Delete")
                                .setMessage("Do you want to delete it?");
                        cfmdel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ContentResolver cR=v.getContext().getContentResolver();
                                Uri delimguri = Uri.parse(d.get(position).imgUri);
                                StorageReference imgref=FirebaseStorage.getInstance().getReferenceFromUrl(d.get(position).imgUri);
                                DocumentReference dref=FirebaseFirestore.getInstance().collection("Doctors").document(d.get(position).Id.toString());
                                imgref.delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dref.delete()
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(v.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(v.getContext(), "Failed To Delete \n Try Again Later", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(v.getContext(), "Failed To Delete \n Try Again Later", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        });
                        cfmdel.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(v.getContext(), "Delete Cancled", Toast.LENGTH_SHORT).show();
                            }
                        });
                        cfmdel.show();
                    }
                });
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                                docdet.dismiss();
                                Intent intent=new Intent(ct,EditDoctor.class);
                                intent.putExtra("Name",d.get(position).name);
                                intent.putExtra("Spec",d.get(position).spec);
                                intent.putExtra("Desc",d.get(position).desc);
                                intent.putExtra("Img",d.get(position).imgUri);
                                intent.putExtra("Work",d.get(position).work);
                                intent.putExtra("Exp",d.get(position).exp);
                                intent.putExtra("Id",d.get(position).Id);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                ct.startActivity(intent);
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        docdet.dismiss();
                    }
                });
                docdet.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return d.size();
    }
    class DocsViewHolder extends RecyclerView.ViewHolder{
        TextView name,spec;
        CardView cv;
        ImageView img;
        public DocsViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.doc_list_name);
            spec=itemView.findViewById(R.id.doc_list_spec);
            cv=itemView.findViewById(R.id.doc_list_cv);
            img=itemView.findViewById(R.id.list_img);
        }
    }
}
