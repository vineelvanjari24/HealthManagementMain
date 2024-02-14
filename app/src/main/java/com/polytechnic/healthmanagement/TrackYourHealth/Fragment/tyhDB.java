package com.polytechnic.healthmanagement.TrackYourHealth.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.polytechnic.healthmanagement.TrackYourHealth.Model.TYHTable;

import java.util.ArrayList;

public class tyhDB extends SQLiteOpenHelper {
    //
    private  static  String TABLE_NAME="USerHealthTable";
    private static  String ID="id";
    private  static  String ISSUE="Issue";
    private  static  String DESCRIPTION="Description";
    private  static  String PROBLEM_RELATED_TO="ProblemRelatedTo";
    private  static  String CREATED_DATE="CreatedDate";
    private  static  String EDITED_DATE="EditedDate";

    //
    private static final String DBNAME="UserHealthDB";
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
        q="CREATE TABLE datacopy(ID Integer,ParaOne text,ParaTwo text)";
        db.execSQL(q);
        db.execSQL("create table "+TABLE_NAME+" (" +ID+ " integer primary key autoincrement ,"  +ISSUE+ " text ,"+DESCRIPTION +" text ,"+PROBLEM_RELATED_TO+" text ,"+ CREATED_DATE+" text , "+EDITED_DATE+" text "+ ")");
    }

    public void addAilment(TYHTable na)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Ailment",na.Name);
        if(na.P1.trim().equals(""))
            na.P1="ParameterOne";
        if(na.P2.trim().equals(""))
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

    public Boolean tableExistsOrNot(String tname){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("Select * from "+TABLENAME,null);
        ArrayList<String> existingTables=new ArrayList<>();
        while(cur.moveToNext()){
            String tns=cur.getString(1);
            existingTables.add(tns);
        }
        if (existingTables.contains(tname))
            return true;
        else
            return false;
    }

    public void addTable(TYHTable table){
        SQLiteDatabase db=this.getWritableDatabase();
        if(table.P1.trim().equals(""))
            table.P1="ParameterOne";
        if(table.P2.trim().equals(""))
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

    public void updateTables(TYHTable tb,String tn){
        SQLiteDatabase db=this.getWritableDatabase();
        String q="SELECT Id from "+TABLENAME+" WHERE Ailment=?";
        Cursor cur=db.rawQuery(q,new String[]{tn});
        int id=0;
        while(cur.moveToNext()) {
            id = cur.getInt(0);
        }
        ContentValues cv=new ContentValues();
        cv.put("Ailment",tb.Name);
        cv.put("POne",tb.P1);
        cv.put("PTwo",tb.P2);
        db.update(TABLENAME,cv,"Id =?",new String[]{String.valueOf(id)});
        db.delete("datacopy",null,null);
        db.execSQL("INSERT INTO datacopy SELECT * FROM "+tn);
        db.execSQL("DROP TABLE IF EXISTS "+tn);
        addTable(tb);
        String copyDataQuery = "INSERT INTO "+tb.Name+ " SELECT * FROM datacopy" ;
        db.execSQL(copyDataQuery);
    }

    public void updateAilmentRecord(TYHTable tb,TYHTable tv,int Id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(tb.P1,tv.P1);
        cv.put(tb.P2,tv.P2);
        db.update(tb.Name,cv,"Id =?",new String[]{String.valueOf(Id)});
    }

    public void deleteAilment(TYHTable tb){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLENAME,"Ailment=?",new String[]{tb.Name});
        db.execSQL("DROP TABLE  IF EXISTS "+tb.Name);
    }

    public int getRecordId(TYHTable tb,int position){
        int Id=0;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur=db.rawQuery("Select * from "+tb.Name,null);
        ArrayList<TYHTable> tables=new ArrayList<>();
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
    public void deleteAilmentRecord(TYHTable t,int Id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(t.Name,"Id=?",new String[]{String.valueOf(Id)});
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        onCreate(db);
    }
}
