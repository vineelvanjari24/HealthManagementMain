package com.polytechnic.healthmanagement.DoctorList.Model;

public class DoctorModel {
    public String name,description,specialist,experience,workingIn,problemRelatedTo;
    public int id;
    public DoctorModel(int id, String name,String description,String specialist,String experience , String workingIn,String problemRelatedTo){
        this.name=name;
        this.description=description;
        this.specialist=specialist;
        this.experience=experience;
        this.workingIn=workingIn;
        this.problemRelatedTo=problemRelatedTo;
        this.id=id;
    }

}
