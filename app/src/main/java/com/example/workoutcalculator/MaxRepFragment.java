package com.example.workoutcalculator;

import static android.content.Context.MODE_PRIVATE;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MaxRepFragment extends Fragment {

    private EditText editWeightNumber;
    private EditText editRepNumber;
    private int repMaxWeightValue = 0;
    private int currRepValue = 1;
    private final TextView[] rmFields = new TextView[10];
    private SharedPreferences sharedPreferences;
    private static final String CURR_KEY_VALUE = "repMaxWeightValue";

    private SQLiteDatabase db;
    private EditText editMovement;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "maxRepParam1";
    private static final String ARG_PARAM2 = "maxRepParam2";

    // Required empty public constructor
    public MaxRepFragment(){}

    @NonNull
    public static MaxRepFragment newInstance(String param1, String param2) {
        MaxRepFragment fragment = new MaxRepFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_max_rep, container, false);

        editWeightNumber = view.findViewById(R.id.editWeightNumber);
        editRepNumber = view.findViewById(R.id.editRepsNumber);
        editMovement = view.findViewById(R.id.editMovement);

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        repMaxWeightValue = sharedPreferences.getInt(CURR_KEY_VALUE, 0);
        currRepValue = sharedPreferences.getInt("secondary_key", 1);

        editWeightNumber.setText(String.valueOf(repMaxWeightValue));
        editRepNumber.setText(String.valueOf(currRepValue));

        ImageView minusWeightButton = view.findViewById(R.id.button_weight_minus);
        ImageView plusWeightButton = view.findViewById(R.id.button_weight_plus);
        ImageView minusButtonReps = view.findViewById(R.id.button_reps_minus);
        ImageView plusButtonReps = view.findViewById(R.id.button_reps_plus);
        ImageView addStats = view.findViewById(R.id.button_add_stat);

        minusWeightButton.setOnClickListener(v -> {
            if (repMaxWeightValue>0){
                repMaxWeightValue-=5;
                editWeightNumber.setText(String.valueOf(repMaxWeightValue));
                updateRMs();
                saveValueToSharedPrefs();
            }
        });

        plusWeightButton.setOnClickListener(v -> {
            repMaxWeightValue+=5;
            editWeightNumber.setText(String.valueOf(repMaxWeightValue));
            updateRMs();
            saveValueToSharedPrefs();
        });

        editWeightNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // If the EditText loses focus, update the currentValue
                String valueStr = editWeightNumber.getText().toString();
                if (!valueStr.isEmpty()) {
                    repMaxWeightValue = Integer.parseInt(valueStr);
                }
                editWeightNumber.setText(String.valueOf(repMaxWeightValue));
            }
        });

        // Listen for done/enter press on the soft keyboard
        editWeightNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // When the user presses "Done" on the soft keyboard, update currentValue
                String valueStr = editWeightNumber.getText().toString();
                if (!valueStr.isEmpty()) {
                    repMaxWeightValue = Integer.parseInt(valueStr);
                }
                editWeightNumber.setText(String.valueOf(repMaxWeightValue));
                return true;
            }
            return false;
        });

        minusButtonReps.setOnClickListener(v -> {
            if (currRepValue>1){
                currRepValue--;
                editRepNumber.setText(String.valueOf(currRepValue));
                updateRMs();
            }
        });

        plusButtonReps.setOnClickListener(v -> {
            currRepValue++;
            editRepNumber.setText(String.valueOf(currRepValue));
            updateRMs();
        });

        rmFields[0] = view.findViewById(R.id._1RM_weight);
        rmFields[1] = view.findViewById(R.id._2RM_weight);
        rmFields[2] = view.findViewById(R.id._3RM_weight);
        rmFields[3] = view.findViewById(R.id._4RM_weight);
        rmFields[4] = view.findViewById(R.id._5RM_weight);
        rmFields[5] = view.findViewById(R.id._6RM_weight);
        rmFields[6] = view.findViewById(R.id._7RM_weight);
        rmFields[7] = view.findViewById(R.id._8RM_weight);
        rmFields[8] = view.findViewById(R.id._9RM_weight);
        rmFields[9] = view.findViewById(R.id._10RM_weight);

        addStats.setOnClickListener(view1 -> {
            String currentDate = currentDate();
            WorkoutData data = null;
            try {
                data = new WorkoutData(0, editMovement.getText().toString(), editWeightNumber.getText().toString(), currentDate);
                Toast.makeText(requireContext(), data.toString(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(requireContext(), "Error creating entry", Toast.LENGTH_SHORT).show();
            }
            WorkoutDatabaseHelper dbHelper = new WorkoutDatabaseHelper(getContext());
            assert data != null;
            dbHelper.addData(data);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Close the database connection when the fragment is destroyed
        if (db != null) {
            db.close();
        }
    }

    private void updateRMs() {
        for(int i=0; i<10; i++){
            double brz = repMaxWeightValue*(36/(37-(double)currRepValue));
            double rmMax = Math.floor((brz*(36-i))/36);
            rmFields[i].setText(String.valueOf(rmMax));
        }
    }

    private void saveValueToSharedPrefs(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURR_KEY_VALUE, repMaxWeightValue);
        editor.putInt("secondary_key", currRepValue);
        editor.apply();
    }

    private String currentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    @Override
    public void onPause(){
        super.onPause();
        saveValueToSharedPrefs();
    }
}