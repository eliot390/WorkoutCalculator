package com.example.workoutcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Button btnMaxReps = findViewById(R.id.maxRepButton);
        Button btnPercent = findViewById(R.id.percentageButton);
        Button btnPlates = findViewById(R.id.plateCountButton);
        Button btnStats = findViewById(R.id.statsButton);

        btnMaxReps.setTextColor(getResources().getColor(R.color.light_blue));

        btnMaxReps.setOnClickListener(view -> {
            btnMaxReps.setTextColor(getResources().getColor(R.color.light_blue));
            btnPercent.setTextColor(getResources().getColor(R.color.white));
            btnPlates.setTextColor(getResources().getColor(R.color.white));
            btnStats.setTextColor(getResources().getColor(R.color.white));
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, MaxRepFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        });

        btnPercent.setOnClickListener(view -> {
            btnMaxReps.setTextColor(getResources().getColor(R.color.white));
            btnPercent.setTextColor(getResources().getColor(R.color.light_blue));
            btnPlates.setTextColor(getResources().getColor(R.color.white));
            btnStats.setTextColor(getResources().getColor(R.color.white));
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, PercentageFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        });

        btnPlates.setOnClickListener(view -> {
            btnMaxReps.setTextColor(getResources().getColor(R.color.white));
            btnPercent.setTextColor(getResources().getColor(R.color.white));
            btnPlates.setTextColor(getResources().getColor(R.color.light_blue));
            btnStats.setTextColor(getResources().getColor(R.color.white));
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, PlatesFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        });

        btnStats.setOnClickListener(view -> {
            btnMaxReps.setTextColor(getResources().getColor(R.color.white));
            btnPercent.setTextColor(getResources().getColor(R.color.white));
            btnPlates.setTextColor(getResources().getColor(R.color.white));
            btnStats.setTextColor(getResources().getColor(R.color.light_blue));
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, StatsFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        });
    }
}