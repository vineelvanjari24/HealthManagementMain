package com.polytechnic.healthmanagement.DoctorList.Fragment;


import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.polytechnic.healthmanagement.DoctorList.DoctorDB;
import com.polytechnic.healthmanagement.DoctorList.DoctorActivity;
import com.polytechnic.healthmanagement.DoctorList.RecycleView.DoctorAdapter;
import com.polytechnic.healthmanagement.MedicalList.Fragment.Medicine;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.UserHealth.RecycleView.UserHealthAdapter;

import java.util.ArrayList;


public class DoctorListFragment extends Fragment {
    private ActivityResultLauncher<Intent> gallerylauncher;
    RecyclerView rv;
    FloatingActionButton fbtn;
    EditText name,exp,spec,work,des,search,website;
    Button save,cancel;
    DocsRecycle dr;
    ImageView img;
    //Image Related
    public final int GALLERY_REQUEST_CODE=1000;
    Uri imageUri;
    ArrayList<Doctor> docs=new ArrayList<>();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference docref=db.collection("Doctors");
    public StorageReference strref= FirebaseStorage.getInstance().getReference("uploads");
    Context context;
    String resource;
    public DoctorListFragment() {
        // Required empty public constructor
    }
    public DoctorListFragment(Context context,String resource) {
        this.context=context;
        this.resource=resource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_doctor_list, container, false);

        fbtn=view.findViewById(R.id.doc_fbtn);
        if(resource.equals("fromUser") || resource.equals("fromMainActivity"))
            fbtn.setVisibility(View.GONE);
        rv=view.findViewById(R.id.rv);
        registerResult();
        Dialog newDoc=new Dialog(context,R.style.Dialogbox_border);
        newDoc.setContentView(R.layout.add_doctor);
        save=newDoc.findViewById(R.id.doc_save);
        cancel=newDoc.findViewById(R.id.doc_cancel);
        name=newDoc.findViewById(R.id.doc_name);
        exp=newDoc.findViewById(R.id.doc_exp);
        spec=newDoc.findViewById(R.id.doc_spec);
        work=newDoc.findViewById(R.id.doc_work);
        des=newDoc.findViewById(R.id.doc_desc);
        img=newDoc  .findViewById(R.id.doc_img);
        website=newDoc.findViewById(R.id.doc_website);

        search=view.findViewById(R.id.dl_search);
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
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                exp.setText("");
                des.setText("");
                work.setText("");
                website.setText("");
                spec.setText("");
                spec.clearFocus();
                img.setOnClickListener(view -> pickImg());
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newDoc.dismiss();
                        Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newDoc.dismiss();
                        Doctor d=new Doctor();
                        d.name=name.getText().toString();
                        d.exp=Integer.parseInt(String.valueOf(exp.getText()));
                        d.spec=spec.getText().toString();
                        d.work=work.getText().toString();
                        d.desc=des.getText().toString();
                        d.website=website.getText().toString();
                        uploadFile(d);
                    }
                });
                newDoc.show();
            }
        });

        return  view;
    }

    public void pickImg(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        gallerylauncher.launch(intent);
    }
    public void registerResult(){
        gallerylauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                try{
                    imageUri=o.getData().getData();
                    img.setImageURI(imageUri);
                }
                catch(Exception e){
                    Toast.makeText(context, "No image Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //For getting file Extension Only :
    private String getFileExtension(Uri uri){
        ContentResolver cR=context.getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadFile(Doctor d) {
        if (imageUri != null) {
            StorageReference fileref = strref.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileref.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            d.imgUri=uri.toString();
                                            docref.add(d).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(context, "Upload Successful", Toast.LENGTH_LONG).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, "Failed to add record to Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Failed to get Download URL", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Exception: " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(context, "Image not Selected", Toast.LENGTH_SHORT).show();
        }
    }
    public void onStart() {
        super.onStart();
        docref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
                if (value != null) {
                    docs.clear();
                    for (QueryDocumentSnapshot ds : value) {
                        Doctor d = new Doctor();
                        d = ds.toObject(Doctor.class);
                        d.Id = ds.getId();
                        docs.add(d);
                    }
                    dr = new DocsRecycle(docs, context,resource);
                    rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    rv.setAdapter(dr);
                }
            }
        });
    }
    public void filter(String text){
        ArrayList<Doctor> newdoclist=new ArrayList<>();
        for (Doctor doc:docs){
            if(doc.name.toLowerCase().contains(text.toLowerCase().trim())){
                newdoclist.add(doc);
            } else if (doc.spec.toString().contains(text.toLowerCase())) {
                newdoclist.add(doc);
            }
        }
        dr.filterList(newdoclist);
    }
    // Inside your Fragment class

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Assuming you have a reference to the hosting Activity
        if (getActivity() != null) {
            // Set the new title to the Toolbar in the hosting Activity
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Doctor List");
        }
    }

}