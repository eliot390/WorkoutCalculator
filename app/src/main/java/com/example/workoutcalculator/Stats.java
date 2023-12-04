package com.example.workoutcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

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
    }
}