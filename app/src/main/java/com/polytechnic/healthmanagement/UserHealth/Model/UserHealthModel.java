package com.polytechnic.healthmanagement.UserHealth.Model;

public class UserHealthModel {
    public String issue,description,problemRelatedTo,createdDate,editedDate;
    public int id;
    public UserHealthModel(int id, String issue, String description,String problemRelatedTo, String createdDate, String editedDate){
        this.issue=issue;
        this.description=description;
        this.problemRelatedTo=problemRelatedTo;
        this.createdDate=createdDate;
        this.editedDate=editedDate;
        this.id=id;
    }

}
