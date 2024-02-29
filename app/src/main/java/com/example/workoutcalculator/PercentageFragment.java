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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PercentageFragment extends Fragment {

    private EditText editWeightNumber;
    private int percentWeightValue = 0;
    private int currWeightValue = 1;
    private final TextView[] percentFields = new TextView[10];

    private SharedPreferences sharedPreferences;
    private static final String CURR_KEY_VALUE = "percentWeightValue";

    private static final String ARG_PARAM1 = "percentParam1";
    private static final String ARG_PARAM2 = "percentParam2";

    public PercentageFragment(){}

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
        View view = inflater.inflate(R.layout.fragment_percentage, container, false);

        editWeightNumber = view.findViewById(R.id.editWeightNumber);
        editWeightNumber.setText(String.valueOf(percentWeightValue));

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        percentWeightValue = sharedPreferences.getInt(CURR_KEY_VALUE, 0);
        currWeightValue = sharedPreferences.getInt("secondary_key", 1);

        ImageView minusButton = view.findViewById(R.id.button_weight_minus);
        ImageView plusButton = view.findViewById(R.id.button_weight_plus);

        minusButton.setOnClickListener(v -> {
            if (percentWeightValue>0){
                percentWeightValue-=5;
                editWeightNumber.setText(String.valueOf(percentWeightValue));
                updatePercent();
                saveValueToSharedPrefs();
            }
        });


        plusButton.setOnClickListener(v -> {
            percentWeightValue+=5;
            editWeightNumber.setText(String.valueOf(percentWeightValue));
            updatePercent();
            saveValueToSharedPrefs();
        });

        editWeightNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // If the EditText loses focus, update the currentValue
                String valueStr = editWeightNumber.getText().toString();
                if (!valueStr.isEmpty()) {
                    percentWeightValue = Integer.parseInt(valueStr);
                }
                editWeightNumber.setText(String.valueOf(percentWeightValue));
            }
        });

        // Listen for done/enter press on the soft keyboard
        editWeightNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // When the user presses "Done" on the soft keyboard, update currentValue
                String valueStr = editWeightNumber.getText().toString();
                if (!valueStr.isEmpty()) {
                    percentWeightValue = Integer.parseInt(valueStr);
                }
                editWeightNumber.setText(String.valueOf(percentWeightValue));
                return true;
            }
            return false;
        });

        percentFields[0] = view.findViewById(R.id._95P_weight);
        percentFields[1] = view.findViewById(R.id._90P_weight);
        percentFields[2] = view.findViewById(R.id._85P_weight);
        percentFields[3] = view.findViewById(R.id._80P_weight);
        percentFields[4] = view.findViewById(R.id._75P_weight);
        percentFields[5] = view.findViewById(R.id._70P_weight);
        percentFields[6] = view.findViewById(R.id._65P_weight);
        percentFields[7] = view.findViewById(R.id._60P_weight);
        percentFields[8] = view.findViewById(R.id._55P_weight);
        percentFields[9] = view.findViewById(R.id._50P_weight);

        updatePercent();

        return view;
    }

    private void updatePercent() {
        double num = percentWeightValue;
        double percent = 95;

        for(int i=0; i<10; i++){
            double result = Math.floor((percent/100)*num);
            percentFields[i].setText(String.valueOf(result));
            percent-=5;
        }
    }

    private void saveValueToSharedPrefs(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURR_KEY_VALUE, percentWeightValue);
        editor.putInt("secondary_key", currWeightValue);
        editor.apply();
    }
}