package com.example.workoutcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDataAdapter extends RecyclerView.Adapter<WorkoutDataAdapter.WorkoutViewHolder> {

    private final List<WorkoutData> workoutDataList = new ArrayList<>();

    public void setWorkoutDataList(List<WorkoutData> workoutDataList) {
        this.workoutDataList.clear();
        this.workoutDataList.addAll(workoutDataList);
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workout, parent, false);

        return new WorkoutViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        WorkoutData workout = workoutDataList.get(position);
        holder.bind(workout);

    }

    @Override
    public int getItemCount() {
        return workoutDataList.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        public TextView movementTextView;
        public TextView weightTextView;
        public TextView dateAddedTextView;
        public ImageView deleteBtn;

        public WorkoutViewHolder(View view) {
            super(view);
            movementTextView = view.findViewById(R.id.movementTextView);
            weightTextView = view.findViewById(R.id.weightTextView);
            dateAddedTextView = view.findViewById(R.id.dateAddedTextView);
            deleteBtn = view.findViewById(R.id.deleteBtn);
        }

        public void bind(WorkoutData workoutData){
            String weightUnit = workoutData.getWeight() + " " + itemView.getContext().getString(R.string.lbs);
            String dateAddedLine = workoutData.getDateAdded() + "\n";
            movementTextView.setText(workoutData.getMovement());
            weightTextView.setText(weightUnit);
            dateAddedTextView.setText(dateAddedLine);
        }
    }
}

