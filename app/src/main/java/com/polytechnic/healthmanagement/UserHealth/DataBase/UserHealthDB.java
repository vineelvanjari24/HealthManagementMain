package com.polytechnic.healthmanagement.UserHealth.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.polytechnic.healthmanagement.UserHealth.Model.UserHealthModel;

import java.util.ArrayList;

public class UserHealthDB extends SQLiteOpenHelper {
    private  static  String DATABASE_NAME="AilmentsDB";
    private  static  String TABLE_NAME="USerHealthTable";
    private static  String ID="id";
    private  static  String ISSUE="Issue";
    private  static  String DESCRIPTION="Description";
    private  static  String PROBLEM_RELATED_TO="ProblemRelatedTo";
    private  static  String CREATED_DATE="CreatedDate";
    private  static  String EDITED_DATE="EditedDate";
    private   SQLiteDatabase dbr=this.getReadableDatabase();
    private SQLiteDatabase dbw=this.getWritableDatabase();


    public UserHealthDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (" +ID+ " integer primary key autoincrement ,"  +ISSUE+ " text ,"+DESCRIPTION +" text ,"+PROBLEM_RELATED_TO+" text ,"+ CREATED_DATE+" text , "+EDITED_DATE+" text "+ ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insert(String issue,String description,String problemRelatedTo,String created_date , String edited_date){
        ContentValues cv = new ContentValues();
        cv.put(ISSUE,issue);
        cv.put(PROBLEM_RELATED_TO,problemRelatedTo);
        cv.put(DESCRIPTION,description);
        cv.put(CREATED_DATE,created_date);
        cv.put(EDITED_DATE,edited_date);
        float flag= dbw.insert(TABLE_NAME,null,cv);
        if(flag>0)
            return true;
        else
            return false;
    }
    public boolean update(int id,String issue,String description,String created_date , String edited_date){
        ContentValues cv = new ContentValues();
        cv.put(ISSUE,issue);
        cv.put(DESCRIPTION,description);
        cv.put(CREATED_DATE,created_date);
        cv.put(EDITED_DATE,edited_date);
        int flag =dbw.update(TABLE_NAME,cv,"id="+id,null);
        if(flag>0)
            return true;
        else
            return false;
    }
    public ArrayList<UserHealthModel> select(){
        Cursor cursor= dbw.rawQuery("select * from "+TABLE_NAME,null);
        ArrayList<UserHealthModel> arrayList = new ArrayList<>();
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            do{
                int id=cursor.getInt(0);
                String issue =cursor.getString(1);
                String  description=cursor.getString(2);
                String problemRelatedTo=cursor.getString(3);
                String createdDate=cursor.getString(4);
                String editedDate=cursor.getString(5);
                UserHealthModel userHealthModel = new UserHealthModel(id,issue,description,problemRelatedTo,createdDate,editedDate);
                arrayList.add(userHealthModel);
            }while (cursor.moveToNext());
        }

        return arrayList;
    }
    public  boolean deleteRecord(int id){
       int flag= dbw.delete(TABLE_NAME,"id="+id,null);
       if(flag==1)
           return true;
       else
           return false;
    }
}