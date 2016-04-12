package com.example.xor.bgvoz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xor on 7.4.16..
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="redVoznje.db";
    public static final String TABLE_BATAJNICA="batajnica_table";
    public static final String TABLE_PAN_MOST="panMost_table";

    public static final String COL_B_1="ID";
    public static final String COL_B_2="NAPOMENA";
    public static final String COL_B_3="BATAJNICA";
    public static final String COL_B_4="ZEMUN_POLJE";
    public static final String COL_B_5="ZEMUN";
    public static final String COL_B_6="TOSIN_BUNAR";
    public static final String COL_B_7="NBG";
    public static final String COL_B_8="PROKOP";
    public static final String COL_B_9="KARADJORDJEV_PARK";
    public static final String COL_B_10="VUKOV_SPOMENIK";
    public static final String COL_B_11="PAN_MOST";

    public static final String COL_P_1="ID";
    public static final String COL_P_11="NAPOMENA";
    public static final String COL_P_10="BATAJNICA";
    public static final String COL_P_9="ZEMUN_POLJE";
    public static final String COL_P_8="ZEMUN";
    public static final String COL_P_7="TOSIN_BUNAR";
    public static final String COL_P_6="NBG";
    public static final String COL_P_5="PROKOP";
    public static final String COL_P_4="KARADJORDJEV_PARK";
    public static final String COL_P_3="VUKOV_SPOMENIK";
    public static final String COL_P_2="PAN_MOST";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
       //SQLiteDatabase db= this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_BATAJNICA+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAPOMENA TEXT,BATAJNICA TEXT,ZEMUN_POLJE TEXT,ZEMUN TEXT,TOSIN_BUNAR TEXT,NBG TEXT,PROKOP TEXT,KARADJORDJEV_PARK TEXT,VUKOV_SPOMENIK TEXT,PAN_MOST TEXT)");
        db.execSQL("create table " + TABLE_PAN_MOST + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAPOMENA TEXT,PAN_MOST TEXT,VUKOV_SPOMENIK TEXT,KARADJORDJEV_PARK TEXT,PROKOP TEXT,NBG TEXT,TOSIN_BUNAR TEXT,ZEMUN TEXT,ZEMUN_POLJE TEXT,BATAJNICA TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_BATAJNICA);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_PAN_MOST);
        onCreate(db);

    }


    public List<String> getTimesBataja(String stanica) {

        List<String> vremena = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT " + stanica +" FROM " + TABLE_BATAJNICA;
        Log.i("query is:",selectQuery+"");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(0);
                if(!time.toString().equals("")){
                    vremena.add(time);}
            } while (cursor.moveToNext());
        }
        db.close();
        // fake data
        /*else if("NBG".matches(stanica)){
            vremena.add("11:35");
            vremena.add("12:35");
            vremena.add("13:35");
            vremena.add("14:35");
            vremena.add("15:35");
        }
        else {
            vremena.add("18:35");
            vremena.add("19:35");
            vremena.add("20:35");
            vremena.add("21:35");
            vremena.add("22:35");
        }*/
        return vremena;
    }


    public List<String> getTimesPanMost(String stanica) {

        List<String> vremena = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT " + stanica +" FROM " + TABLE_PAN_MOST;

        SQLiteDatabase db = this.getWritableDatabase();//   assuming that this is connection to the database
        Cursor cursor = db.rawQuery(selectQuery, null);//   passing query to the db

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(0);
                if(!time.toString().equals("")){
                vremena.add(time);}

            } while (cursor.moveToNext());
        }
        db.close();
        // fake data
        /*else if("NBG".matches(stanica)){
            vremena.add("11:35");
            vremena.add("12:35");
            vremena.add("13:35");
            vremena.add("14:35");
            vremena.add("15:35");
        }
        else {
            vremena.add("18:35");
            vremena.add("19:35");
            vremena.add("20:35");
            vremena.add("21:35");
            vremena.add("22:35");
        }*/
        return vremena;
    }


}
