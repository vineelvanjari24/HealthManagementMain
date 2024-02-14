package com.polytechnic.healthmanagement.MedicalList.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.polytechnic.healthmanagement.R;

import java.util.ArrayList;


public class MedicalListFragment extends Fragment {
    CollectionReference cref= FirebaseFirestore.getInstance().collection("MedicalList");
    ArrayList<Medicine> mlarr=new ArrayList<>();
    MedAdapter medadpt;
    RecyclerView mlrv;
    FloatingActionButton mlfbtn;
    Button save,cancel;
    EditText cn,cf,md,search;
    Context context;
    public MedicalListFragment() {
        // Required empty public constructor
    }
    public MedicalListFragment(Context context) {
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_medical_list, container, false);

        mlrv=view.findViewById(R.id.ml_rv);
        mlfbtn=view.findViewById(R.id.mlfbtn);
        search=view.findViewById(R.id.ml_search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {filter(s.toString());
            }
        });
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    search.clearFocus();
                    search.setText("");
                    onStart();
                    return true;
                }
                return false;
            }
        });

        mlfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog newmed=new Dialog(getContext());
                newmed.setContentView(R.layout.med_add_dialog);
                newmed.setCancelable(false);

                cn=newmed.findViewById(R.id.ml_edit_cn);
                cf=newmed.findViewById(R.id.ml_edit_cf);
                md=newmed.findViewById(R.id.ml_edit_med);
                save=newmed.findViewById(R.id.ml_save);
                cancel=newmed.findViewById(R.id.ml_cancel);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newmed.dismiss();
                        Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newmed.dismiss();
                        Medicine med=new Medicine();
                        med.cn=cn.getText().toString();
                        med.cf=cf.getText().toString();
                        med.med=md.getText().toString();
                        cref.add(med)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed To Add to Online Database", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                newmed.show();
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        cref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(context, "Error : "+error, Toast.LENGTH_SHORT).show();
                }
                else{
                    mlarr.clear();
                    for(QueryDocumentSnapshot qd:value) {
                        Medicine newmedicine = new Medicine();
                        newmedicine =qd.toObject(Medicine.class);
                        newmedicine.Id= qd.getId();
                        mlarr.add(newmedicine);
                    }
                    medadpt=new MedAdapter(context,mlarr);
                    mlrv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                    mlrv.setAdapter(medadpt);
                }
            }
        });
    }
    public void filter(String text){
        ArrayList<Medicine> newmelist=new ArrayList<>();
        for (Medicine medic:mlarr){
            if(medic.cn.toLowerCase().contains(text.toLowerCase().trim())){
                newmelist.add(medic);
            } else if (medic.med.toString().contains(text.toLowerCase())) {
                newmelist.add(medic);
            } else if (medic.cf.toString().contains(text.toLowerCase())) {
                newmelist.add(medic);
            }
        }
        medadpt.filterList(newmelist);
    }
}