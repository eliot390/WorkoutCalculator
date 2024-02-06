package com.example.workoutcalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StatsFragment extends Fragment {

    private static final String ARG_PARAM1 = "statsParam1";
    private static final String ARG_PARAM2 = "statsParam2";

    public StatsFragment(){}

    @NonNull
    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        TextView noTextView = view.findViewById(R.id.noTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        WorkoutDataAdapter workoutAdapter = new WorkoutDataAdapter();

        recyclerView.setAdapter(workoutAdapter);

        try (WorkoutDatabaseHelper dbHelper = new WorkoutDatabaseHelper(getActivity())){
            List<WorkoutData> workoutDataList = dbHelper.getData();

            if(workoutDataList.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                noTextView.setVisibility(View.VISIBLE);
            }else{
                recyclerView.setVisibility(View.VISIBLE);
                noTextView.setVisibility(View.GONE);
                workoutAdapter.setWorkoutDataList(workoutDataList);
            }
        }

        recyclerView.setAdapter(workoutAdapter);

        return view;
    }
}