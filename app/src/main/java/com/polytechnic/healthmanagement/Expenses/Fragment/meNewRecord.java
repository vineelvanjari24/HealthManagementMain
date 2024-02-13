package com.polytechnic.healthmanagement.Expenses.Fragment;
public class meNewRecord {
    public String date;
    public String title;
    public int other,medexp,docfee,trans;
    public int tamt;
    public void tt(){
        tamt=other+medexp+docfee+trans;
    }
}
