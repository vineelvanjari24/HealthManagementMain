package com.polytechnic.healthmanagement.DoctorList.RecycleView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.polytechnic.healthmanagement.DoctorList.DoctorDB;
import com.polytechnic.healthmanagement.DoctorList.Model.DoctorModel;
import com.polytechnic.healthmanagement.MainActivity;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.UserHealth.DataBase.UserHealthDB;
import com.polytechnic.healthmanagement.UserHealth.Model.UserHealthModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    Context context;
    ArrayList<DoctorModel> arrayList;
    String source;
    public DoctorAdapter(Context context, String source){
        this.context = context;
        DoctorDB doctorDB = new DoctorDB(context);
        arrayList=doctorDB.select();
        this.source=source;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_recycle_view_design,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.doctorNameTV.setText(arrayList.get(position).name);
        holder.descriptionTV.setText(arrayList.get(position).description);
        holder.cardView.setOnClickListener(v ->{
            if(source.equals("fromAdmin")){

            }
            else if(source.equals("")) {

            }
            else{

            }
        });
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView doctorNameTV,descriptionTV;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorNameTV=itemView.findViewById(R.id.doctorName);
            descriptionTV=itemView.findViewById(R.id.doctorDescription);
            cardView=itemView.findViewById(R.id.doctorListCardview);
        }
    }
}
