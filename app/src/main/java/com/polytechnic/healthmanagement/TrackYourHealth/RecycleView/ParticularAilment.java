package com.polytechnic.healthmanagement.TrackYourHealth.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.TrackYourHealth.Fragment.Ailment;
import com.polytechnic.healthmanagement.TrackYourHealth.Fragment.tyhDB;
import com.polytechnic.healthmanagement.TrackYourHealth.Model.TYHTable;

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
        String cusmdate = new SimpleDateFormat("yyyy-mm-dd").format(d1);
        holder.date.setText(cusmdate);
        if (tb.P1.equals("")) {
            holder.tv1.setVisibility(View.INVISIBLE);
            holder.tv2.setVisibility(View.INVISIBLE);
        }
        else {
            holder.tv1.setText(tb.P1);
            holder.tv2.setText(table.get(position).P1);
        }
        if (tb.P2.equals("")) {
            holder.tv3.setVisibility(View.INVISIBLE);
            holder.tv4.setVisibility(View.INVISIBLE);
        }
        else {
            holder.tv3.setText(tb.P2);
            holder.tv4.setText(table.get(position).P2);
        }

    }
    @Override
    public int getItemCount(){
            return table.size();
        }

    class ParticularAilmentViewHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2,tv3,tv4,date;
        public ParticularAilmentViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.tyh_ailment_date);
            tv1=itemView.findViewById(R.id.tyh_pa_t1);
            tv2=itemView.findViewById(R.id.tyh_pa_t2);
            tv3=itemView.findViewById(R.id.tyh_pa_t3);
            tv4=itemView.findViewById(R.id.tyh_pa_t4);
        }
    }

}
