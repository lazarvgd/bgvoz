package com.example.xor.bgvoz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
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

    private static String DB_PATH = "/data/data/com.example.xor.bgvoz/databases/";
    private static String DB_NAME = "redVoznje.db";

    private SQLiteDatabase myDataBase;

    private SQLiteDatabase mDataBase;
    private final Context mContext;


/*
    public static final String DATABASE_NAME="redVoznje.db";
*/
    public static final String TABLE_BATAJNICA="batajnica_table";
    public static final String TABLE_PAN_MOST="panMost_table";

   /* public static final String COL_B_1="ID";
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
*/

    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){

        }else{

            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {

        InputStream myInput = mContext.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<String> getTimesBataja(String polaznaStanica) {

        List<String> vremena = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT " + polaznaStanica +" FROM " + TABLE_BATAJNICA;

        Log.i("query is:",selectQuery+"");
        SQLiteDatabase db = this.getWritableDatabase();

        String queryForChoosenStartTime="SELECT * FROM "+TABLE_BATAJNICA +"WHERE ";//komentar u sledecem redu
        //ovde treba dodati broj ili sta god da vraca spinner kada se izabere vrijeme polaska. na osnovu toga
        // treba da vrati cio taj red i da ispise u view-u

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

        return vremena;
    }


    public List<String> getTimesPanMost(String polaznaStanica) {

        List<String> vremena = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT " + polaznaStanica +" FROM " + TABLE_PAN_MOST;

        Log.i("query is:",selectQuery+"");
        SQLiteDatabase db = this.getWritableDatabase();//   assuming that this is connection to the database
        Cursor cursor = db.rawQuery(selectQuery, null);//   passing query to the db

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(0);

                if(!time.toString().equals(""))
                    vremena.add(time);

                Log.d("SQLite", cursor.getString(0));

            } while (cursor.moveToNext());
        }
        db.close();

        return vremena;
    }


}
