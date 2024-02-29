package com.example.workoutcalculator;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {

    private WorkoutDatabaseHelper dbHelper;
    private Spinner spinner;
    private List<WorkoutData> allData;
    private List<WorkoutData> filteredData;

    private static final String ARG_PARAM1 = "statsParam1";
    private static final String ARG_PARAM2 = "statsParam2";

    public StatsFragment(){}

//    @NonNull
//    public static StatsFragment newInstance(String param1, String param2) {
//        StatsFragment fragment = new StatsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            String mParam1 = getArguments().getString(ARG_PARAM1);
//            String mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        spinner = view.findViewById(R.id.menu_spinner);
        dbHelper = new WorkoutDatabaseHelper(getActivity());

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;
        params.height = (int) (screenHeight * 0.6);
        recyclerView.setLayoutParams(params);

        TextView noTextView = view.findViewById(R.id.noTextView);
        ImageView graphIcon = view.findViewById(R.id.graphIcon);
        graphIcon.setVisibility(View.GONE);

        graphIcon.setOnClickListener(view1 -> {
            String selectedMovement = (String) spinner.getSelectedItem();

            GraphFragment graphFragment = new GraphFragment();
            Bundle args = new Bundle();
            args.putString("selectedMovement", selectedMovement);
            graphFragment.setArguments(args);

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, graphFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        WorkoutDataAdapter workoutAdapter = new WorkoutDataAdapter();
        recyclerView.setAdapter(workoutAdapter);

        populateMenu();

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String selectedMovement = (String) adapterView.getItemAtPosition(pos);
                List<WorkoutData> filteredWorkoutDataList = dbHelper.getDataByMovement(selectedMovement);
                List<WorkoutData> workoutDataList = dbHelper.getData();

                if (filteredWorkoutDataList.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    graphIcon.setVisibility(View.GONE);
                    noTextView.setVisibility(View.GONE);
                    workoutAdapter.setWorkoutDataList(workoutDataList);
                    workoutAdapter.notifyDataSetChanged();
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    graphIcon.setVisibility(View.VISIBLE);
                    noTextView.setVisibility(View.GONE);
                    workoutAdapter.setWorkoutDataList(filteredWorkoutDataList);
                    workoutAdapter.notifyDataSetChanged(); // Notify adapter about the data change
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void populateMenu() {
        List<String> movementNames = dbHelper.getDistinctMovements();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, movementNames);
        spinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        movementNames.add(0, "Select Movement");
    }

}