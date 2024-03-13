package com.polytechnic.healthmanagement.UserHealth.RecycleView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.polytechnic.healthmanagement.MainActivity;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.UserHealth.AddUserHealthActivity;
import com.polytechnic.healthmanagement.UserHealth.DataBase.UserHealthDB;
import com.polytechnic.healthmanagement.UserHealth.Model.UserHealthModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            {
                Dialog dialog = new Dialog(context,R.style.Dialogbox_border);
                dialog.setContentView(R.layout.activity_add_user_health);
                /*Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
*/
                LinearLayout linearLayout = dialog.findViewById(R.id.linearLayout);
                LinearLayout toolbar = dialog.findViewById(R.id.toolbar);
                linearLayout.removeView(toolbar);
                UserHealthDB userHealthDB = new UserHealthDB(context);
                EditText issueET, descriptionET;
                TextView issueTV, descriptionTV;
                TextInputLayout issueTextInputLayout, descriptionTextInputLayout;
                String issueString, descriptionString;
                ImageView edit, delete, cancel;
                edit = dialog.findViewById(R.id.edit);
                delete = dialog.findViewById(R.id.delete);
                cancel = dialog.findViewById(R.id.cancel);
                issueET=dialog.findViewById(R.id.issueUserHealth);
                descriptionET=dialog.findViewById(R.id.descriptionUserHealth);
                Spinner problemRelatedToSpinner = dialog.findViewById(R.id.problemRelatedToSpinner);
                Button add=dialog.findViewById(R.id.add);
                issueTextInputLayout = dialog.findViewById(R.id.issueTextInputLayout);
                descriptionTextInputLayout = dialog.findViewById(R.id.descriptionTextInputLayout);
                issueTV = dialog.findViewById(R.id.issueUserHealthTV);
                descriptionTV = dialog.findViewById(R.id.descriptionUserHealthTV);

                ArrayList<String> problemRelatedToArrayList =AddUserHealthActivity.probleRelatedToArrayList();
                ArrayAdapter problemRelatedToAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1,problemRelatedToArrayList);
                problemRelatedToSpinner.setAdapter(problemRelatedToAdapter);

               issueTV.setText("Issue");
                issueTextInputLayout.setHint("Issue");
                descriptionTV.setText("Description");
                descriptionTextInputLayout.setHint("Description");
                int id = arrayList.get(position).id;
                issueString = arrayList.get(position).issue;
                descriptionString = arrayList.get(position).description;
                String spinnerString = arrayList.get(position).problemRelatedTo;
                String createdDate = arrayList.get(position).createdDate;
                int swapInt = problemRelatedToArrayList.indexOf(spinnerString);
                String swapString = problemRelatedToArrayList.get(0);
                problemRelatedToArrayList.set(0, spinnerString);
                problemRelatedToArrayList.set(swapInt, swapString);
                problemRelatedToAdapter.notifyDataSetChanged();
                problemRelatedToSpinner.setAdapter(problemRelatedToAdapter);

                issueET.setText(issueString);
                descriptionET.setText(descriptionString);
                issueET.setFocusable(false);
                issueET.setClickable(false);
                issueET.setFocusableInTouchMode(false);
                issueET.setCursorVisible(false);
              /*  issueET.setTextColor(getResources().getColor(R.color.lightBlack));
                descriptionET.setTextColor(getResources().getColor(R.color.lightBlack));
              */  descriptionET.setFocusable(false);
                descriptionET.setClickable(false);
                descriptionET.setFocusableInTouchMode(false);
                descriptionET.setCursorVisible(false);
                problemRelatedToSpinner.setEnabled(false);

                add.setOnClickListener(va ->{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.Dialogbox_border);
                    builder.setMessage("DO YOU WANT TO RECOMMEND DOCTOR ??");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("flag",true);
                            intent.putExtra("value",problemRelatedToSpinner.getSelectedItem().toString());
                            context.startActivity(intent);
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    builder.show();
                });
                edit.setOnClickListener(va -> {
                    issueET.setFocusable(true);
                    issueET.setClickable(true);
                    issueET.setFocusableInTouchMode(true);
                    issueET.setCursorVisible(true);
                 /*   issueET.setTextColor(getResources().getColor(R.color.black));
                    descriptionET.setTextColor(getResources().getColor(R.color.black));
                 */   descriptionET.setFocusable(true);
                    descriptionET.setClickable(true);
                    descriptionET.setFocusableInTouchMode(true);
                    descriptionET.setCursorVisible(true);
                    problemRelatedToSpinner.setEnabled(true);
                    issueTV.setText("Enter Issue");
                    descriptionTV.setText("Enter Description");
                    issueET.setHint("Enter Issue");
                    descriptionET.setHint("Enter Description");
                    add.setText("save");
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String issueString1=issueET.getText().toString();
                            String descriptionString1 = descriptionET.getText().toString().trim();
                            if (issueString1.isEmpty() || descriptionString1.isEmpty()) {
                                if (issueString1.isEmpty() && descriptionString1.isEmpty())
                                    Toast.makeText(context, "Please enter all details", Toast.LENGTH_SHORT).show();
                                else if (issueString1.isEmpty())
                                    Toast.makeText(context, "Please enter issue ", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(context, "Please enter description ", Toast.LENGTH_SHORT).show();
                            } else {
                                Date currentDate = new Date();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy h:mm:ss a"); // Add "ss" for seconds
                                String formattedDateTime = dateFormat.format(currentDate);
                                String date = formattedDateTime.substring(0, 8);
                                userHealthDB.update(id, issueString1, descriptionString1, createdDate, date);
                                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                arrayList.set(position,new UserHealthModel(id,issueString1,descriptionString1,problemRelatedToSpinner.getSelectedItem().toString(),createdDate,date));
                                add.setText("Next");
                                issueET.setFocusable(false);
                                issueET.setClickable(false);
                                issueET.setFocusableInTouchMode(false);
                                issueET.setCursorVisible(false);
                        /*    issueET.setTextColor(getResources().getColor(R.color.lightBlack));
                            descriptionET.setTextColor(getResources().getColor(R.color.lightBlack));
                        */    descriptionET.setFocusable(false);
                                descriptionET.setClickable(false);
                                descriptionET.setFocusableInTouchMode(false);
                                descriptionET.setCursorVisible(false);
                                problemRelatedToSpinner.setEnabled(false);
                                issueTV.setText("Issue");
                                issueTextInputLayout.setHint("Issue");
                                descriptionTV.setText("Description");
                                descriptionTextInputLayout.setHint("Description");

                            }
                        }
                    });

                });
                delete.setOnClickListener(va -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.Dialogbox_border);
                    builder.setTitle("DELETE RECORD ?");
                    builder.setIcon(R.drawable.delete);
                    builder.setCancelable(false);
                    builder.setMessage("ARE YOU SURE WANT TO DELETE THIS RECORD ??");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            userHealthDB.deleteRecord(id);
                            arrayList.remove(position);
                            notifyItemRemoved(position);
                                dialogInterface.dismiss();
                                dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                });
                cancel.setOnClickListener(va ->{
                    dialog.dismiss();
                });
                dialog.show();
            }
           /* Intent intent = new Intent(context, AddUserHealthActivity.class);
            intent.getBooleanExtra("flag",false);
            intent.putExtra("issue",arrayList.get(position).issue);
            intent.putExtra("description",arrayList.get(position).description);
            intent.putExtra("spinner",arrayList.get(position).problemRelatedTo);
            intent.putExtra("id",arrayList.get(position).id);
            intent.putExtra("createdDate",arrayList.get(position).createdDate);
            context.startActivity(intent);*/

        });
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {
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
