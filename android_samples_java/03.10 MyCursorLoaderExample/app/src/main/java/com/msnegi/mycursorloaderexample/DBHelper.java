package com.msnegi.mycursorloaderexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    static final String DATABASE_NAME = "BirthdayDatabase";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "Birthday";

    // fields for the database
    static final String ID = "_id";
    static final String NAME = "name";
    static final String BIRTHDAY = "birthday";

    static final String CREATE_TABLE_BIRTHDAY = " CREATE TABLE " + TABLE_NAME +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " name TEXT NOT NULL, " +
            " birthday TEXT NOT NULL);";

    private static final String DROP_TABLE_BIRTHDAY = "DROP TABLE IF EXISTS " + CREATE_TABLE_BIRTHDAY;

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_BIRTHDAY);

        db.execSQL("INSERT INTO Birthday(_id, name, birthday) VALUES ('1', 'Aman','1-Jan-2017')");
        db.execSQL("INSERT INTO Birthday(_id, name, birthday) VALUES ('2', 'Chamn','5-Feb-1982')");
        db.execSQL("INSERT INTO Birthday(_id, name, birthday) VALUES ('3', 'Pawan','25-Mar-2014')");
        db.execSQL("INSERT INTO Birthday(_id, name, birthday) VALUES ('4', 'Sandeep','2-Jan-2006')");
        db.execSQL("INSERT INTO Birthday(_id, name, birthday) VALUES ('5', 'deepak','16-May-2012')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_BIRTHDAY);
        onCreate(db);
    }
}
