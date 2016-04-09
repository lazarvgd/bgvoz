package com.example.xor.bgvoz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    SQLiteDatabase db= this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_BATAJNICA+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAPOMENA TEXT,BATAJNICA TEXT,ZEMUN_POLJE TEXT,ZEMUN TEXT,TOSIN_BUNAR TEXT,NBG TEXT,PROKOP TEXT,KARADJORDJEV_PARK TEXT,VUKOV_SPOMENIK TEXT,PAN_MOST TEXT)");
        db.execSQL("create table "+TABLE_PAN_MOST+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAPOMENA TEXT,PAN_MOST TEXT,VUKOV_SPOMENIK TEXT,KARADJORDJEV_PARK TEXT,PROKOP TEXT,NBG TEXT,TOSIN_BUNAR TEXT,ZEMUN TEXT,ZEMUN_POLJE TEXT,BATAJNICA TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_BATAJNICA);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_PAN_MOST);
        onCreate(db);
    }
}