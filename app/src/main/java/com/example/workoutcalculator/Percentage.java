package com.example.workoutcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Percentage extends AppCompatActivity {

    private EditText editWeightNumber;

    private SharedPreferences sharedPreferences;
    private static final String CURR_VALUE_KEY = "percentWeightValue";

    private int percentWeightValue = 0;

    private final TextView[] percentFields = new TextView[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        percentWeightValue = sharedPreferences.getInt(CURR_VALUE_KEY, 0);

        editWeightNumber = findViewById(R.id.editWeightNumber);
        editWeightNumber.setText(String.valueOf(percentWeightValue));

        ImageView barbellButton = findViewById(R.id.barbell);
        barbellButton.setOnClickListener(v -> {
            Intent intent = new Intent(Percentage.this, MainActivity.class);
            startActivity(intent);
        });

        ImageView statsButton = findViewById(R.id.stats);
        statsButton.setOnClickListener(v -> {
            Intent intent = new Intent(Percentage.this, Stats.class);
            startActivity(intent);
        });

        ImageView minusButton = findViewById(R.id.button_weight_minus);
        minusButton.setOnClickListener(v -> {
            if (percentWeightValue>0){
                percentWeightValue-=5;
                editWeightNumber.setText(String.valueOf(percentWeightValue));
                updatePercent();
                saveValueToSharedPrefs();
            }
        });

        ImageView plusButton = findViewById(R.id.button_weight_plus);
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

        percentFields[0] = findViewById(R.id._95P_weight);
        percentFields[1] = findViewById(R.id._90P_weight);
        percentFields[2] = findViewById(R.id._85P_weight);
        percentFields[3] = findViewById(R.id._80P_weight);
        percentFields[4] = findViewById(R.id._75P_weight);
        percentFields[5] = findViewById(R.id._70P_weight);
        percentFields[6] = findViewById(R.id._65P_weight);
        percentFields[7] = findViewById(R.id._60P_weight);

        updatePercent();
    }

    private void updatePercent() {
        double num = percentWeightValue;
        double percent = 95;

        for(int i=0; i<8; i++){
            double result = Math.floor((percent/100)*num);
            percentFields[i].setText(String.valueOf(result));
            percent-=5;
        }
    }

    private void saveValueToSharedPrefs(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURR_VALUE_KEY, percentWeightValue);
        editor.apply();
    }
}