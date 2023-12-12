package com.example.workoutcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PlateCount extends AppCompatActivity {

    private EditText editWeightValue;
    private int barbellWeight;
    private int totalWeightValue = 0;
    private final TextView[] platesFields = new TextView[8];

    private SharedPreferences sharedPreferences;
    private static final String CURR_VALUE_KEY = "totalWeightValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_count);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        totalWeightValue = sharedPreferences.getInt(CURR_VALUE_KEY, 0);

        editWeightValue = findViewById(R.id.totalWeight);
        editWeightValue.setText(String.valueOf(totalWeightValue));

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radio35 = findViewById(R.id.radio35);
        radio35.setChecked(true);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio35){
                barbellWeight = 35;
            }else if (checkedId == R.id.radio45){
                barbellWeight = 45;
            }
        });

        ImageView barbellButton = findViewById(R.id.barbell);
        barbellButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlateCount.this, MainActivity.class);
            startActivity(intent);
        });

        ImageView percentButton = findViewById(R.id.percentSign);
        percentButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlateCount.this, Percentage.class);
            startActivity(intent);
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

        Button yourButton = findViewById(R.id.button);
        yourButton.setOnClickListener(v -> {
            calculate(v);
            saveValueToSharedPrefs();
        });

        platesFields[0] = findViewById(R.id._45PlateCount);
        platesFields[1] = findViewById(R.id._35PlateCount);
        platesFields[2] = findViewById(R.id._25PlateCount);
        platesFields[3] = findViewById(R.id._15PlateCount);
        platesFields[4] = findViewById(R.id._10PlateCount);
        platesFields[5] = findViewById(R.id._5PlateCount);
        platesFields[6] = findViewById(R.id._halfPlateCount);
        platesFields[7] = findViewById(R.id._1PlateCount);
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