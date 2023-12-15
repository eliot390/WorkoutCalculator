package com.example.workoutcalculator;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PlatesFragment extends Fragment {

    private EditText editWeightValue;
    private int barbellWeight;
    private int totalWeightValue = 0;
    private final TextView[] platesFields = new TextView[8];

    private SharedPreferences sharedPreferences;
    private static final String CURR_VALUE_KEY = "totalWeightValue";

    private static final String ARG_PARAM1 = "platesParam1";
    private static final String ARG_PARAM2 = "platesParam2";

    public PlatesFragment(){}

    @NonNull
    public static PlatesFragment newInstance(String param1, String param2) {
        PlatesFragment fragment = new PlatesFragment();
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
        View view = inflater.inflate(R.layout.fragment_plates, container, false);

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        totalWeightValue = sharedPreferences.getInt(CURR_VALUE_KEY, 0);

        editWeightValue = view.findViewById(R.id.totalWeight);
        editWeightValue.setText(String.valueOf(totalWeightValue));

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton radio35 = view.findViewById(R.id.radio35);
        radio35.setChecked(true);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio35){
                barbellWeight = 35;
            }else if (checkedId == R.id.radio45){
                barbellWeight = 45;
            }
        });

        editWeightValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // If the EditText loses focus, update the currentValue
                String valueStr = editWeightValue.getText().toString();
                if (!valueStr.isEmpty()) {
                    totalWeightValue = Integer.parseInt(valueStr);
                }
                editWeightValue.setText(String.valueOf(totalWeightValue));
            }
        });

        // Listen for done/enter press on the soft keyboard
        editWeightValue.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // When the user presses "Done" on the soft keyboard, update currentValue
                String valueStr = editWeightValue.getText().toString();
                if (!valueStr.isEmpty()) {
                    totalWeightValue = Integer.parseInt(valueStr);
                }
                editWeightValue.setText(String.valueOf(totalWeightValue));
                return true;
            }
            return false;
        });

        Button yourButton = view.findViewById(R.id.button);
        yourButton.setOnClickListener(v -> {
            calculate(v);
            saveValueToSharedPrefs();
        });

        platesFields[0] = view.findViewById(R.id._45PlateCount);
        platesFields[1] = view.findViewById(R.id._35PlateCount);
        platesFields[2] = view.findViewById(R.id._25PlateCount);
        platesFields[3] = view.findViewById(R.id._15PlateCount);
        platesFields[4] = view.findViewById(R.id._10PlateCount);
        platesFields[5] = view.findViewById(R.id._5PlateCount);
        platesFields[6] = view.findViewById(R.id._halfPlateCount);
        platesFields[7] = view.findViewById(R.id._1PlateCount);

        return view;
    }

    private void calculate(View v) {
        int plateWeight = totalWeightValue - barbellWeight;
        int[] plateValues = {45, 35, 25, 15, 10, 5, (int) 2.5, 1}; // Plate values in descending order
        int[] platesCount = new int[plateValues.length];

        for (int i = 0; i < plateValues.length; i++) {
            platesCount[i] = plateWeight / plateValues[i];
            if (platesCount[i] % 2 != 0) {
                platesCount[i] -= 1;
            }
            platesFields[i].setText(String.valueOf(platesCount[i]));
            plateWeight -= platesCount[i] * plateValues[i];
        }
    }

    private void saveValueToSharedPrefs(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURR_VALUE_KEY, totalWeightValue);
        editor.apply();
    }
}