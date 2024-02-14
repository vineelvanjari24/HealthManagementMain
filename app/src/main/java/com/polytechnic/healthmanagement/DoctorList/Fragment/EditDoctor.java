package com.polytechnic.healthmanagement.DoctorList.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.polytechnic.healthmanagement.R;
import com.squareup.picasso.Picasso;

public class EditDoctor extends AppCompatActivity {
    private ActivityResultLauncher<Intent> gallerylauncher;
    public final int GALLERY_REQUEST_CODE=1000;
    Uri imageUri;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference docref=db.collection("Doctors");
    public StorageReference strref= FirebaseStorage.getInstance().getReference("uploads");
    Button save,cancel;
    TextView name,exp,spec,work,des,website;
    ImageView img;
    Doctor editDoc=new Doctor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor);

        Intent intent=getIntent();
        editDoc.name=intent.getStringExtra("Name");
        editDoc.spec=intent.getStringExtra("Spec");
        editDoc.exp=intent.getIntExtra("Exp",0);
        editDoc.desc=intent.getStringExtra("Desc");
        editDoc.imgUri=intent.getStringExtra("Img");
        editDoc.work=intent.getStringExtra("Work");
        editDoc.Id=intent.getStringExtra("Id");
        editDoc.website=intent.getStringExtra("Website");

        registerResult();

        save=findViewById(R.id.edoc_save);
        cancel=findViewById(R.id.edoc_cancel);
        name=findViewById(R.id.edoc_name);
        exp=findViewById(R.id.edoc_exp);
        spec=findViewById(R.id.edoc_spec);
        work=findViewById(R.id.edoc_work);
        des=findViewById(R.id.edoc_desc);
        website=findViewById(R.id.edit_doc_website);
        img=findViewById(R.id.edoc_img);

        img.setOnClickListener(view->pickImg());
        name.setText(editDoc.name);
        spec.setText(editDoc.spec);
        work.setText(editDoc.work);
        des.setText(editDoc.desc);
        website.setText(editDoc.website);
        exp.setText(String.valueOf(editDoc.exp));

        Picasso.get().load(editDoc.imgUri).into(img);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditDoctor.this, "Edit Canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doctor d=new Doctor();
                d.name=name.getText().toString();
                d.exp=Integer.parseInt(String.valueOf(exp.getText()));
                d.spec=spec.getText().toString();
                d.work=work.getText().toString();
                d.desc=des.getText().toString();
                d.website=website.getText().toString();
                uploadFile(d);
                ContentResolver cR=v.getContext().getContentResolver();
                Uri delimguri = Uri.parse(editDoc.imgUri);
                StorageReference imgref=FirebaseStorage.getInstance().getReferenceFromUrl(editDoc.imgUri);
                DocumentReference dref=FirebaseFirestore.getInstance().collection("Doctors").document(editDoc.Id.toString());
                if(editDoc.imgUri != d.imgUri) {
                    imgref.delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dref.delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

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
                else{
                    dref.delete()
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), "Failed To Delete \n Try Again Later", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                finish();
            }
        });

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
                    Toast.makeText(EditDoctor.this, "No image Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //For getting file Extension Only :
    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
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
                                                    Toast.makeText(EditDoctor.this, "Edited Successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(EditDoctor.this, "Failed to edit record : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditDoctor.this, "Failed to get Image Download URL", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditDoctor.this, "Exception: " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
        } else
        {
            d.imgUri= editDoc.imgUri;
            docref.add(d).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(EditDoctor.this, "Edited Successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditDoctor.this, "Failed to edit record : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}