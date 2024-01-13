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
    public ArrayList<TYHTable> tables=new ArrayList<>();
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
        String q="CREATE TABLE "+table.Name+"(Id Integer PRIMARY KEY AUTOINCREMENT,"+table.P1+" text,"+table.P2+" text)";
        db.execSQL(q);
    }

    public void addAilmentRecord(TYHTable t,TYHTable data){
        ContentValues ct=new ContentValues();
        ct.put(t.P1,data.P1);
        ct.put(t.P2,data.P2);
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(t.Name,null,ct);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        onCreate(db);
    }
}
