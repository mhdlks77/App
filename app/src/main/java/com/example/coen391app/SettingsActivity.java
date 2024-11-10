package com.example.coen391app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

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
        Button wifiSetup_btn = findViewById(R.id.wifi_setup_button);


        wifiSetup_btn.setOnClickListener(v -> {
            // Check for ACCESS_FINE_LOCATION permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                Toast.makeText(this, "Please press again after accepting permissions!", Toast.LENGTH_SHORT).show();

            }else {
                // If permission is granted, show the DialogFragment
                DialogFragment dialogFragment = new WifiSetup();
                dialogFragment.show(getSupportFragmentManager(), "Wifi Setup");
            }

        });



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