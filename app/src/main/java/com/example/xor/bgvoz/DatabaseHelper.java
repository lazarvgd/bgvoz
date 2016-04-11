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

    private  Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db= this.getWritableDatabase();
        this.myContext = context;

        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
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



    //The Android's default system path of your application database.
    private static String DB_PATH;

    private static String DB_NAME = DATABASE_NAME;

    private SQLiteDatabase myDataBase;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
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

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
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

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.




    public List<String> getTimesBataja(String stanica) {

        List<String> vremena = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT " + stanica +" FROM " + TABLE_BATAJNICA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(0);
                vremena.add(time);

            } while (cursor.moveToNext());
        }
        // fake data
        else if("NBG".matches(stanica)){
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
        }

        // return contact list
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
                vremena.add(time);

            } while (cursor.moveToNext());
        }
        // fake data
        else if("NBG".matches(stanica)){
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
        }

        // return contact list
        return vremena;
    }


}
