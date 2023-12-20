package com.example.workoutcalculator;

public final class StatsContract {
    private StatsContract() {} // Private constructor to prevent instantiation

    public static class StatsEntry {
        public static final String TABLE_NAME = "stats";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DATA = "data";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_DATA + " TEXT)";
    }

}
