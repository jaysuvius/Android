package com.example.term.termmanager.Dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.term.termmanager.Utils.Constants;

public class DbOpenHelper extends SQLiteOpenHelper {
    //Constants for db name and version
    private static final String DATABASE_NAME = "TermManager.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for identifying table and columns

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.TERMS_TABLE_CREATE);
        db.execSQL(Constants.COURSES_TABLE_CREATE);
        db.execSQL(Constants.ASSESSMENTS_TABLE_CREATE);
        db.execSQL(Constants.NOTES_TABLE_CREATE);
        db.execSQL(Constants.IMAGES_TABLE_CREATE);
        db.execSQL(Constants.MENTORS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TERMS_TABLE);
        db.execSQL(Constants.TERMS_TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.COURSES_TABLE);
        db.execSQL(Constants.COURSES_TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.ASSESSMENTS_TABLE);
        db.execSQL(Constants.ASSESSMENTS_TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.NOTES_TABLE);
        db.execSQL(Constants.NOTES_TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.IMAGES_TABLE);
        db.execSQL(Constants.IMAGES_TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.MENTORS_TABLE);
        db.execSQL(Constants.MENTORS_TABLE_CREATE);

    }

}
