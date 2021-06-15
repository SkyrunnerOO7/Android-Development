package com.crm.pvt.hapinicrm.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {


    public static final String DBName = "Login.db";

    public DBhelper(@Nullable Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
        mydb.execSQL("create table Admin(Passcode Text Primary key,Name Text,email Text,password Text)");
        mydb.execSQL("create table Employee(number Text ,email Text primary key ,password Text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase mydb, int i, int i1) {

        mydb.execSQL("drop table if exists Admin");
        mydb.execSQL("drop table if exists Employee");

    }

    public boolean insertdata(String passcode,String name,String email,String password){
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("passcode",passcode);
        values.put("name",name);
        values.put("email",email);
        values.put("password",password);

        long results;
        results = mydb.insert("Admin",null,values);
        if(results==-1) return false;
        else return true;
    }

    public boolean checkAdminpasscode(int passcode){
        return true;

    }

    public boolean checkAdminEmail(String email){
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("select * from Admin where email = ?",new String[]{ email});

        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
    public boolean checkAdminpasword(String email,String password){
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("select * from Admin where email = ? and password  = ? ",new String[]{email,password});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }

    }

    public boolean insertdataEmpoyee(String number ,String email,String password){
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IMEI",number);
        values.put("email",email);
        values.put("password",password);

        long results;
        results = mydb.insert("Employee",null,values);
        if(results==-1) return false;
        else return true;
    }

    public boolean checkEmployeeEmail(String email){
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("select * from Employee where email = ?",new String[]{email});

        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkEmpoyeepasword(String email,String password){
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("select * from Employee where email = ? and password  = ? ",new String[]{email,password});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }

    }







}
