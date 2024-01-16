package com.polytechnic.healthmanagement.DoctorList.Model;

public class DoctorModel {
    public String name,description,specialist,experience,workingIn;
    public int id;
    public DoctorModel(int id, String name,String description,String specialist,String experience , String workingIn){
        this.name=name;
        this.description=description;
        this.specialist=specialist;
        this.experience=experience;
        this.workingIn=workingIn;
        this.id=id;
    }

}
