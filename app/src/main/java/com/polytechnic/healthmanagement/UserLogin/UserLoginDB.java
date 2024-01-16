package com.polytechnic.healthmanagement.UserLogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserLoginDB extends SQLiteOpenHelper{

    public UserLoginDB(@Nullable Context context) {
        super(context, "userLoginDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table userLogin (id integer primary key autoincrement , EmailID text , userPassword text ) ");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists  userLogin");
        onCreate(db);
    }
    public Boolean userInsert(String userName,String userPassword){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("EmailID", userName);
        cv.put("userPassword", userPassword);
        long flag=db.insert("userLogin",null, cv);
        if(flag==-1)
            return false;
        else
            return true;
    }
    public Boolean UsernameChecked(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" select EmailID from userLogin where EmailID =? ", new String[] {name});
        if(cursor.getCount()==1)
            return true;
        else
            return false;
    }
public boolean loginCheck(String name , String password){
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor =  db.rawQuery("select * from userLogin where EmailID =? and  userPassword =?", new String[] {name,password});
    if(cursor.getCount()==1)
        return true;
    else
        return false;
    }
    public boolean deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        int deleteRow=db.delete("userLogin","EmailID=?",new String[] {username});
        if(deleteRow==1)
            return true;
        else
            return false;
    }
    public boolean changePassword(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userPassword",password);
        int updateRow = db.update("userLogin",cv, "EmailID = ?",new String[] {username});
        if(updateRow==1)
            return true;
        else
            return false;
    }
}

