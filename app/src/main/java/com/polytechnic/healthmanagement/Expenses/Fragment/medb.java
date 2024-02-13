package com.polytechnic.healthmanagement.Expenses.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.polytechnic.healthmanagement.TrackYourHealth.Model.TYHTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class medb extends SQLiteOpenHelper {
    public static final String TABLENAME="ExpensesRecords";
    public static final String DBNAME="MedicalExpenses";
    public static final int DBVERSION=1;
    public medb(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLENAME+"(Id Integer PRIMARY KEY AUTOINCREMENT,Date text,Title text,doctorFee Integer,MedicinesCost Integer,TransCost Integer,Others Integer)");
    }

    public void addRecord(meNewRecord mnr){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Date",mnr.date);
        cv.put("Title",mnr.title);
        cv.put("doctorFee",mnr.docfee);
        cv.put("MedicinesCost",mnr.medexp);
        cv.put("TransCost",mnr.trans);
        cv.put("Others",mnr.other);
        db.insert(TABLENAME,null,cv);
    }

    public ArrayList<meNewRecord> readRecords(){
        ArrayList<meNewRecord> arr=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cur=db.rawQuery("SELECT * FROM "+TABLENAME,null);
        while(cur.moveToNext()){
            meNewRecord mnr=new meNewRecord();
            mnr.date=cur.getString(1);
            mnr.title=cur.getString(2);
            mnr.docfee=cur.getInt(3);
            mnr.medexp=cur.getInt(4);
            mnr.trans=cur.getInt(5);
            mnr.other=cur.getInt(6);
            arr.add(mnr);
        }
        return arr;
    }

    public int getMeId(int position){
        int Id=0;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("Select * from "+TABLENAME,null);
        while(position>0){
            cur.moveToNext();
            position--;
        }
        if(cur.moveToNext()){
            Id=cur.getInt(0);
        }
        cur.close();
        db.close();
        return Id;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        onCreate(db);
    }

    public void deleteMeRecord(int id) {
    SQLiteDatabase db=this.getWritableDatabase();
    db.delete(TABLENAME,"Id=?",new String[]{String.valueOf(id)});
    }

    public ArrayList<meNewRecord> analyzeRecords(String searchstring){
        ArrayList<meNewRecord> arr=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM "+TABLENAME+" WHERE Date LIKE '"+searchstring+"%'";
        Cursor cur=db.rawQuery(query,null);
        while(cur.moveToNext()){
            meNewRecord mnr=new meNewRecord();
            mnr.date=cur.getString(1);
            mnr.title=cur.getString(2);
            mnr.docfee=cur.getInt(3);
            mnr.medexp=cur.getInt(4);
            mnr.trans=cur.getInt(5);
            mnr.other=cur.getInt(6);
            arr.add(mnr);
        }
        return arr;
    }

    public void updateMeRecord(meNewRecord mnr,int id) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Date",mnr.date);
        cv.put("Title",mnr.title);
        cv.put("doctorFee",mnr.docfee);
        cv.put("MedicinesCost",mnr.medexp);
        cv.put("TransCost",mnr.trans);
        cv.put("Others",mnr.other);
        db.update(TABLENAME,cv,"Id= ?",new String[]{String.valueOf(id)});
    }
}
