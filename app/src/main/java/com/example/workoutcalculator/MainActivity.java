package com.example.workoutcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editWeightNumber;

    private SharedPreferences sharedPreferences;
    private static final String CURR_VALUE_KEY = "currWeightValue";

    private int currWeightValue = 0;
    private EditText editRepNumber;
    private int currRepValue = 1;

    private final TextView[] rmFields = new TextView[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        currWeightValue = sharedPreferences.getInt(CURR_VALUE_KEY, 0);

        editWeightNumber = findViewById(R.id.editWeightNumber);
        editWeightNumber.setText(String.valueOf(currWeightValue));
        editRepNumber = findViewById(R.id.editRepsNumber);
        editRepNumber.setText(String.valueOf(currRepValue));

        Button plusButtonWeight = findViewById(R.id.button_weight_plus);
        Button minusButtonWeight = findViewById(R.id.button_weight_minus);
        Button plusButtonReps = findViewById(R.id.button_reps_plus);
        Button minusButtonReps = findViewById(R.id.button_reps_minus);

        plusButtonWeight.setOnClickListener(v -> {
            currWeightValue+=5;
            editWeightNumber.setText(String.valueOf(currWeightValue));
            updateRMs();
            saveValueToSharedPrefs();
        });

        minusButtonWeight.setOnClickListener(v -> {
            if (currWeightValue>0){
                currWeightValue-=5;
                editWeightNumber.setText(String.valueOf(currWeightValue));
                updateRMs();
                saveValueToSharedPrefs();
            }
        });

        editWeightNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // If the EditText loses focus, update the currentValue
                String valueStr = editWeightNumber.getText().toString();
                if (!valueStr.isEmpty()) {
                    currWeightValue = Integer.parseInt(valueStr);
                }
                editWeightNumber.setText(String.valueOf(currWeightValue));
            }
        });

        // Listen for done/enter press on the soft keyboard
        editWeightNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // When the user presses "Done" on the soft keyboard, update currentValue
                String valueStr = editWeightNumber.getText().toString();
                if (!valueStr.isEmpty()) {
                    currWeightValue = Integer.parseInt(valueStr);
                }
                editWeightNumber.setText(String.valueOf(currWeightValue));
                return true;
            }
            return false;
        });

        plusButtonReps.setOnClickListener(v -> {
            currRepValue++;
            editRepNumber.setText(String.valueOf(currRepValue));
            updateRMs();
        });

        minusButtonReps.setOnClickListener(v -> {
            if (currRepValue>0){
                currRepValue--;
                editRepNumber.setText(String.valueOf(currRepValue));
                updateRMs();
            }
        });
        
        rmFields[0] = findViewById(R.id._1RM_weight);
        rmFields[1] = findViewById(R.id._2RM_weight);
        rmFields[2] = findViewById(R.id._3RM_weight);
        rmFields[3] = findViewById(R.id._4RM_weight);
        rmFields[4] = findViewById(R.id._5RM_weight);
        rmFields[5] = findViewById(R.id._6RM_weight);
        rmFields[6] = findViewById(R.id._7RM_weight);
        rmFields[7] = findViewById(R.id._8RM_weight);
        
        updateRMs();
    }

    private void updateRMs() {
        for(int i=0; i<8; i++){
            double brz = currWeightValue*(36/(37-(double)currRepValue));
            double rmMax = Math.floor((brz*(36-i))/36);
            rmFields[i].setText(String.valueOf(rmMax));
        }
    }

    private void saveValueToSharedPrefs(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURR_VALUE_KEY, currWeightValue);
        editor.apply();
    }
}