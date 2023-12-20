package com.example.workoutcalculator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {

    private EditText addMovementEditText;
    private List<String> movementList;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper databaseHelper;
    private Spinner spinner;

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
        databaseHelper = new DatabaseHelper(getActivity());
        spinner = view.findViewById(R.id.spinner);

        //loadMovementsToSpinner();

        movementList = new ArrayList<>();
        movementList.add("Deadlift");
        movementList.add("Bench Press");
        movementList.add("Back Squat");

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, movementList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String selectedMovement = movementList.get(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addMovementEditText = view.findViewById(R.id.editText);
        view.findViewById(R.id.addButton).setOnClickListener(v -> {
            String newMovement = addMovementEditText.getText().toString().trim();
            if (!newMovement.isEmpty()){
                movementList.add(newMovement);
                adapter.notifyDataSetChanged();
                spinner.setSelection(movementList.indexOf(newMovement));
            }
        });

//        addMovementEditText = view.findViewById(R.id.editText);
//        Button addButton = view.findViewById(R.id.addButton);
//        addButton.setOnClickListener(v -> addMovementToSpinner());
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = adapterView.getItemAtPosition(i).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("Bench Press");
//        arrayList.add("Deadlift");
//        arrayList.add("Overhead Press");
//        arrayList.add("Snatch");
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, arrayList);
//        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
//        spinner.setAdapter(adapter);
//
//        editText = view.findViewById(R.id.editText);
//        Button addButton = view.findViewById(R.id.addButton);
//
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addItem();
//            }
//        });
        return view;
    }

//    private void addMovementToSpinner() {
//        String newData = addMovementEditText.getText().toString().trim();
//
//        if (!newData.isEmpty()) {
//            SQLiteDatabase db = databaseHelper.getWritableDatabase();
//            ContentValues values = new ContentValues();
//            values.put(DatabaseHelper.COLUMN_DATA, newData);
//
//            // Insert the new data into the database
//            long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
//
//            if (newRowId != -1) {
//                // If insertion is successful, update the spinner with new data
//                loadMovementsToSpinner();
//                addMovementEditText.setText(""); // Clear the input field
//            } else {
//                // Handle insertion failure
//            }
//        } else {
//            // Handle empty input
//        }
//    }
//
//    private void loadMovementsToSpinner() {
//        SQLiteDatabase statsDatabase = databaseHelper.getReadableDatabase();
//        Cursor cursor = statsDatabase.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
//
//        ArrayList<String> spinnerData = new ArrayList<>();
//        if (cursor.moveToFirst()) {
//            do {
//                String data = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATA));
//                spinnerData.add(data);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerData);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//    }
}