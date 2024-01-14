package com.polytechnic.healthmanagement.TrackYourHealth.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.polytechnic.healthmanagement.TrackYourHealth.Model.TYHTable;

import java.util.ArrayList;

public class tyhDB extends SQLiteOpenHelper {
    private static final String DBNAME="AilmentsDB";
    private static final int DBVERSION=1;
    public static final String TABLENAME="AilmentsList";
    public tyhDB(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q;
        q = "CREATE TABLE "+TABLENAME+"(Id Integer PRIMARY KEY AUTOINCREMENT,Ailment text,POne text,PTwo text)";
        db.execSQL(q);
    }

    public void addAilment(TYHTable na){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Ailment",na.Name);
        if(na.P1.trim().equals("") || na.P1.trim()==null)
            na.P1="ParameterOne";
        if(na.P2.trim().equals("") || na.P2.trim()==null)
            na.P2="ParameterTwo";
        cv.put("POne",na.P1);
        cv.put("PTwo",na.P2);
        db.insert(TABLENAME,null,cv);
    }

    public ArrayList<TYHTable> readAilments(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("Select * from "+TABLENAME,null);
        ArrayList<TYHTable> tables=new ArrayList<>();
        while(cur.moveToNext()){
            TYHTable t1=new TYHTable();
            t1.Name=cur.getString(1);
            t1.P1=cur.getString(2);
            t1.P2=cur.getString(3);
            tables.add(t1);
        }
        return tables;
    }

    public void addTable(TYHTable table){
        SQLiteDatabase db=this.getWritableDatabase();
        if(table.P1.trim().equals("") || table.P1.trim() == null)
            table.P1="ParameterOne";
        if(table.P2.trim().equals("") || table.P1.trim() == null)
            table.P2="ParameterTwo";
        String q="CREATE TABLE "+table.Name+"(Id Integer PRIMARY KEY AUTOINCREMENT,"+table.P1+" text,"+table.P2+" text)";
        db.execSQL(q);
    }

    public void addAilmentRecord(TYHTable t,TYHTable data){
        ContentValues ct=new ContentValues();
        if(t.P1.equals("ParameterOne") || t.P1.equals("ParameterTwo"))
            data.P1="noInserted";
        if(t.P2.equals("ParameterOne") || t.P2.equals("ParameterTwo"))
            data.P2="notInserted";
        ct.put(t.P1,data.P1);
        ct.put(t.P2,data.P2);
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(t.Name,null,ct);
    }

    public ArrayList<TYHTable> readParticularAilment(String tn){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("Select * from "+tn,null);
        ArrayList<TYHTable> tables=new ArrayList<>();
        while(cur.moveToNext()){
            TYHTable t1=new TYHTable();
            t1.P1=cur.getString(1);
            t1.P2=cur.getString(2);
            tables.add(t1);
        }
        return tables;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        onCreate(db);
    }
}
