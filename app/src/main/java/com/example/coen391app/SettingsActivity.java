package com.example.coen391app;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout waterSettingsLayout;
    private LinearLayout uvSettingsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        waterSettingsLayout = findViewById(R.id.waterSettingsLayout);
        uvSettingsLayout = findViewById(R.id.uvSettingsLayout);
        TextView waterSettingsHeader = findViewById(R.id.waterSettingsHeader);
        TextView uvSettingsHeader = findViewById(R.id.uvSettingsHeader);

        // Water Settings
        waterSettingsHeader.setOnClickListener(view -> {
            if (waterSettingsLayout.getVisibility() == View.VISIBLE) {
                waterSettingsLayout.setVisibility(View.GONE);
            } else {
                waterSettingsLayout.setVisibility(View.VISIBLE);
            }
        });

        // UV
        uvSettingsHeader.setOnClickListener(view -> {
            if (uvSettingsLayout.getVisibility() == View.VISIBLE) {
                uvSettingsLayout.setVisibility(View.GONE);
            } else {
                uvSettingsLayout.setVisibility(View.VISIBLE);
            }
        });


        SeekBar lowSoilMoistureSlider = findViewById(R.id.lowSoilMoistureSlider);
        SeekBar wateringSlider = findViewById(R.id.wateringSlider);
        Switch wateringSwitch = findViewById(R.id.wateringSwitch);
        SeekBar uvSlider = findViewById(R.id.uvSlider);
        Switch themeSwitch = findViewById(R.id.themeSwitch);

        // ADD ADDITIONAL LOGIC HERE
    }
}
