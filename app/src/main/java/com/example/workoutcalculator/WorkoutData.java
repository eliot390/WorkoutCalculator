package com.example.workoutcalculator;

import androidx.annotation.NonNull;

public class WorkoutData {
    private int id;
    private String movement;
    private String weight;
    private String dateAdded;

    public WorkoutData(int id, String movement, String weight, String dateAdded) {
        this.id = id;
        this.movement = movement;
        this.weight = weight;
        this.dateAdded = dateAdded;
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkoutData{" +
                "id=" + id +
                ", movement='" + movement + '\'' +
                ", weight='" + weight + '\'' +
                ", dateAdded='" + dateAdded + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovement() {
        return movement;
    }

    public String getWeight() {
        return weight;
    }

    public String getDateAdded() {
        return dateAdded;
    }

}
