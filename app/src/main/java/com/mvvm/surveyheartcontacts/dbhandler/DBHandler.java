package com.mvvm.surveyheartcontacts.dbhandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHandler extends SQLiteOpenHelper {

    private final String TAG = "DBHandler";
    private static final String DATABASE_NAME = "SurveyHeart.db";

    public static final int DATABASE_VERSION = 1;

    private static Context context;
    private static DBHandler dbHandler;
    public static String TABLE_CONTACTS = "Contacts";


    public static DBHandler getInstance(Context context2) {
        if (dbHandler == null) {
            context = context2;
            dbHandler = new DBHandler(context2);
        }
        return dbHandler;
    }

    public static SQLiteDatabase getReadableDb(Context context) {
        return getInstance(context).getReadableDatabase();
    }

    public static SQLiteDatabase getWritableDb(Context context) {
        return getInstance(context).getWritableDatabase();
    }

    public SQLiteDatabase getDBObject(int isWritable) {
        return (isWritable == 1) ? this.getWritableDatabase() : this.getReadableDatabase();
    }

    /**
     * Constructor
     *
     * @param context :Interface to global information about an application
     *                environment.
     */
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database operations", "Database is created");
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should
     * happen.
     *
     * @param db : The database.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS + "(id integer PRIMARY KEY AUTOINCREMENT, name TEXT, mobileNumber TEXT, email NUMBER, contactType TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

