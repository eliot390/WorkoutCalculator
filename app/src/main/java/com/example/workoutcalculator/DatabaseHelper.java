package com.example.workoutcalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "StatsDatabase";
    public static final int DATABASE_VERSION = 1;
    public static String TABLE_NAME = "stats_db";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATA = "data";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase statsDatabase) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATA + " TEXT)";
        statsDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase statsDatabase, int oldVersion, int newVersion) {
        statsDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(statsDatabase);
    }
}
