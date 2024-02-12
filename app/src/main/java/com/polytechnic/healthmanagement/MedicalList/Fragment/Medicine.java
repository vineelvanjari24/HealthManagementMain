package com.polytechnic.healthmanagement.MedicalList.Fragment;

import com.google.firebase.firestore.Exclude;

public class Medicine {
    @Exclude
    String Id;
    public String cn;
    public String med;
    public String cf;
    public Medicine(){
    }
}
