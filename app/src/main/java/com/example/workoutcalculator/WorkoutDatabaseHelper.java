package com.example.workoutcalculator;

import android.annotation.SuppressLint;
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

    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORKOUT, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
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

    public List<String> getDistinctMovements(){
        List<String> movementNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_WORKOUT, new String[]{COLUMN_MOVEMENT}, null, null, COLUMN_MOVEMENT, null, null, null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    String movementName = cursor.getString(0);
                    movementNames.add(movementName);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return movementNames;
    }

    public List<WorkoutData> getDataByMovement(String movement){
        List<WorkoutData> workoutDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_MOVEMENT + " = ?";
        String[] selections = {movement};
        Cursor cursor = db.query(TABLE_WORKOUT, null, selection, selections, null, null, null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String movementValue = cursor.getString(cursor.getColumnIndex(COLUMN_MOVEMENT));
                @SuppressLint("Range") String weight = cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_ADDED));

                WorkoutData data = new WorkoutData(id, movementValue, weight, date);
                workoutDataList.add(data);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return workoutDataList;
    }

}
