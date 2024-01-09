package com.example.workoutcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "workout_data.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_WORKOUT = "workout";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MOVEMENT = "movement";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_DATE_ADDED = "date_added";

    // SQL statement to create the table
    private static final String SQL_CREATE_WORKOUT_TABLE = "CREATE TABLE " + TABLE_WORKOUT + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MOVEMENT + " TEXT, " +
            COLUMN_WEIGHT + " TEXT, " +
            COLUMN_DATE_ADDED + " TEXT " +
            ")";

    public WorkoutDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_WORKOUT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
        onCreate(db);
    }

    public void addData(WorkoutData workoutData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_MOVEMENT, workoutData.getMovement());
        values.put(COLUMN_WEIGHT, workoutData.getWeight());
        values.put(COLUMN_DATE_ADDED, workoutData.getDateAdded());

        db.insert(TABLE_WORKOUT, null, values);
    }

    public void deleteData(WorkoutData workoutData){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_WORKOUT + " WHERE " + COLUMN_ID + " = " + workoutData.getId();

        Cursor cursor = db.rawQuery(query, null);

        cursor.close();
        db.close();
    }

    public List<WorkoutData> getData(){
        List<WorkoutData> workoutList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_WORKOUT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String movement = cursor.getString(1);
                String weight = cursor.getString(2);
                String date = cursor.getString(3);

                WorkoutData data = new WorkoutData(id, movement, weight, date);
                workoutList.add(data);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return workoutList;
    }
}
