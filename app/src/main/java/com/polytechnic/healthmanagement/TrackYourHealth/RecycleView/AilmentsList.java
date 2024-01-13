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

import java.util.ArrayList;

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

    @NonNull
    @Override
    public AilmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf=LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.ailments_list_layout,parent,false);
        return new AilmentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AilmentsViewHolder holder, int position) {
        holder.tv.setText(tables.get(position).Name);
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
        public AilmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tyh_ailmentdisplay);
            cv=itemView.findViewById(R.id.TYH_Ailmentlist_cv);
        }
    }

}
