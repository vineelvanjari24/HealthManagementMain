package com.polytechnic.healthmanagement.DoctorList.Fragment;

import com.google.firebase.firestore.Exclude;

public class Doctor {
    @Exclude
    public String Id;
    public String name;
    public String imgUri;
    public int exp;
    public String spec;
    public String work;
    public String desc;
}
