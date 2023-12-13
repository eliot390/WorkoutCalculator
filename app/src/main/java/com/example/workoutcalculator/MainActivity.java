package com.example.workoutcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private EditText editWeightNumber;
    private EditText editRepNumber;
    private int repMaxWeightValue = 0;
    private int currRepValue = 1;
    private final TextView[] rmFields = new TextView[10];

    private SharedPreferences sharedPreferences;
    private static final String CURR_VALUE_KEY = "repMaxWeightValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editWeightNumber = findViewById(R.id.editWeightNumber);
        editWeightNumber.setText(String.valueOf(repMaxWeightValue));
        editRepNumber = findViewById(R.id.editRepsNumber);
        editRepNumber.setText(String.valueOf(currRepValue));

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        repMaxWeightValue = sharedPreferences.getInt(CURR_VALUE_KEY, 0);

        ImageView percentButton = findViewById(R.id.percentSign);
        percentButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Percentage.class);
            startActivity(intent);
        });

        ImageView platesButton = findViewById(R.id.plates);
        platesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PlateCount.class);
            startActivity(intent);
        });

        ImageView minusButtonWeight = findViewById(R.id.button_weight_minus);
        minusButtonWeight.setOnClickListener(v -> {
            if (repMaxWeightValue>0){
                repMaxWeightValue-=5;
                editWeightNumber.setText(String.valueOf(repMaxWeightValue));
                updateRMs();
                saveValueToSharedPrefs();
            }
        });

        ImageView plusButtonWeight = findViewById(R.id.button_weight_plus);
        plusButtonWeight.setOnClickListener(v -> {
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

        ImageView minusButtonReps = findViewById(R.id.button_reps_minus);
        minusButtonReps.setOnClickListener(v -> {
            if (currRepValue>1){
                currRepValue--;
                editRepNumber.setText(String.valueOf(currRepValue));
                updateRMs();
            }
        });

        ImageView plusButtonReps = findViewById(R.id.button_reps_plus);
        plusButtonReps.setOnClickListener(v -> {
            currRepValue++;
            editRepNumber.setText(String.valueOf(currRepValue));
            updateRMs();
        });

        rmFields[0] = findViewById(R.id._1RM_weight);
        rmFields[1] = findViewById(R.id._2RM_weight);
        rmFields[2] = findViewById(R.id._3RM_weight);
        rmFields[3] = findViewById(R.id._4RM_weight);
        rmFields[4] = findViewById(R.id._5RM_weight);
        rmFields[5] = findViewById(R.id._6RM_weight);
        rmFields[6] = findViewById(R.id._7RM_weight);
        rmFields[7] = findViewById(R.id._8RM_weight);
        rmFields[8] = findViewById(R.id._9RM_weight);
        rmFields[9] = findViewById(R.id._10RM_weight);

        updateRMs();
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
        editor.putInt(CURR_VALUE_KEY, repMaxWeightValue);
        editor.apply();
    }
}