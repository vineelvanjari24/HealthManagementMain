package com.polytechnic.healthmanagement.DoctorList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.polytechnic.healthmanagement.DoctorList.Model.DoctorModel;
import com.polytechnic.healthmanagement.UserHealth.Model.UserHealthModel;

import java.util.ArrayList;

public class DoctorDB extends SQLiteOpenHelper
{
    private  static  String DATABASE_NAME="DoctorDB";
    private  static  String TABLE_NAME="DoctorTable";
    private static  String ID="ID";
    private  static  String NAME="Name";
    private  static  String DESCRIPTION="Description";
    private  static  String SPECIALIST="Specialist";

    private  static  String EXPERIENCE="Experience";
    private  static  String WORKING_IN="WorkingIn";
    private  static  String PROBLEM_RELATED_TO="ProblemRelatedTo";
    private   SQLiteDatabase dbr=this.getReadableDatabase();
    private SQLiteDatabase dbw=this.getWritableDatabase();
    public DoctorDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (" +ID+ " integer primary key autoincrement ,"  +NAME+ " text ,"+DESCRIPTION +" text ,"+SPECIALIST+" text ,"+ EXPERIENCE+" text , "+WORKING_IN+" text ,"+ PROBLEM_RELATED_TO+ " text" +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insert(String name,String description,String specialist,String experience , String workingIn,String problemRelatedTo){
        ContentValues cv = new ContentValues();
        cv.put(NAME,name);
        cv.put(DESCRIPTION,description);
        cv.put(SPECIALIST,specialist);
        cv.put(EXPERIENCE,experience);
        cv.put(WORKING_IN,workingIn);
        cv.put(PROBLEM_RELATED_TO,problemRelatedTo);
        float flag= dbw.insert(TABLE_NAME,null,cv);
        if(flag>0)
            return true;
        else
            return false;
    }
    public ArrayList<DoctorModel> select(){
        Cursor cursor= dbw.rawQuery("select * from "+TABLE_NAME,null);
        ArrayList<DoctorModel> arrayList = new ArrayList<>();
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            do{
                int id=cursor.getInt(0);
                String name =cursor.getString(1);
                String  description=cursor.getString(2);
                String specialist=cursor.getString(3);
                String experience=cursor.getString(4);
                String workingIn=cursor.getString(5);
                String problemRelatedTo=cursor.getString(5);
                DoctorModel doctorModel = new DoctorModel(id,name,description,specialist,experience,workingIn,problemRelatedTo);
                arrayList.add(doctorModel);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }
}
