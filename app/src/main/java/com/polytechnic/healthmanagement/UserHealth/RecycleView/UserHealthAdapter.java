package com.polytechnic.healthmanagement.UserHealth.RecycleView;

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
import com.polytechnic.healthmanagement.UserHealth.Model.UserHealthModel;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class UserHealthAdapter extends RecyclerView.Adapter<UserHealthAdapter.ViewHolder> {
    Context context;
    ArrayList<UserHealthModel> arrayList;
    public UserHealthAdapter(Context context, ArrayList<UserHealthModel> arrayList){
        this.context = context;
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_health_recycle_view_design,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.issueTV.setText(arrayList.get(position).issue);
        holder.descriptionTV.setText(arrayList.get(position).description);
        holder.createdDateTV.setText(arrayList.get(position).createdDate);
        holder.editedDateTV.setText(arrayList.get(position).editedDate);
        holder.cardView.setOnClickListener(v ->{
            Intent intent = new Intent(context,UserHealthAdapter.class);


        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView issueTV,descriptionTV,createdDateTV,editedDateTV;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            issueTV=itemView.findViewById(R.id.issueUserHealth);
            descriptionTV=itemView.findViewById(R.id.descriptionUserHealth);
            createdDateTV=itemView.findViewById(R.id.createdDateUserHealth);
            editedDateTV=itemView.findViewById(R.id.editedDateUserHealth);
            cardView=itemView.findViewById(R.id.userHealthCardView);
        }
    }
}
