package com.example.workoutcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Stats extends AppCompatActivity {

    private EditText editWeightValue;

    private RadioGroup radioGroup;
    private int barbellWeight;

    private SharedPreferences sharedPreferences;
    private static final String CURR_VALUE_KEY = "totalWeightValue";

    private int totalWeightValue = 0;

    private final TextView[] platesFields = new TextView[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        totalWeightValue = sharedPreferences.getInt(CURR_VALUE_KEY, 0);

        editWeightValue = findViewById(R.id.totalWeight);
        editWeightValue.setText(String.valueOf(totalWeightValue));

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio35){
                barbellWeight = 35;
            }else if (checkedId == R.id.radio45){
                barbellWeight = 45;
            }
        });

        ImageView barbellButton = findViewById(R.id.barbell);
        barbellButton.setOnClickListener(v -> {
            Intent intent = new Intent(Stats.this, MainActivity.class);
            startActivity(intent);
        });

        ImageView percentButton = findViewById(R.id.percentSign);
        percentButton.setOnClickListener(v -> {
            Intent intent = new Intent(Stats.this, Percentage.class);
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
    }

    private void calculate(View v) {
        int plateWeight = totalWeightValue - barbellWeight;
        int currWeight = 0;
        int plates45 = 0;
        int plates35 = 0;
        int plates25 = 0;
        int plates15 = 0;
        int plates10 = 0;
        int plates5 = 0;
        int platesHalfLb = 0;

        plates45 = plateWeight/45;
        if(plates45%2 != 0){
            plates45 -= 1;
        }
        platesFields[0].setText(String.valueOf(plates45));
        currWeight = plateWeight-(plates45*45);

        plates35 = currWeight/35;
        if(plates35%2 != 0){
            plates35 -= 1;
        }
        platesFields[1].setText(String.valueOf(plates35));
        currWeight -= (plates35*35);

        plates25 = currWeight/25;
        if(plates25%2 != 0){
            plates25 -= 1;
        }
        platesFields[2].setText(String.valueOf(plates25));
        currWeight -= (plates25*25);

        plates15 = currWeight/15;
        if(plates15%2 != 0){
            plates15 -= 1;
        }
        platesFields[3].setText(String.valueOf(plates15));
        currWeight -= (plates15*15);

        plates10 = currWeight/10;
        if(plates10%2 != 0){
            plates10 -= 1;
        }
        platesFields[4].setText(String.valueOf(plates10));
        currWeight -= (plates10*10);

        plates5 = currWeight/5;
        if(plates5%2 != 0){
            plates5 -= 1;
        }
        platesFields[5].setText(String.valueOf(plates5));
        currWeight -= (plates5*5);

        platesHalfLb = (int) (currWeight/2.5);
        if(platesHalfLb%2 != 0){
            platesHalfLb -= 1;
        }
        platesFields[6].setText(String.valueOf(platesHalfLb));
        currWeight -= (platesHalfLb*2.5);

    }

    private void saveValueToSharedPrefs(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURR_VALUE_KEY, totalWeightValue);
        editor.apply();
    }

//    private void showPopupMenu(View view) {
//        PopupMenu popupMenu = new PopupMenu(this, view);
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.menu_item_1:
//                        // Handle menu item 1 click
//                        return true;
//                    case R.id.menu_item_2:
//                        // Handle menu item 2 click
//                        return true;
//                    // Add cases for other menu items if needed
//                    default:
//                        return false;
//                }
//            }
//        });
//
//        popupMenu.inflate(R.menu.pop_menu); // Inflate the menu resource
//        popupMenu.show();
//    }

}