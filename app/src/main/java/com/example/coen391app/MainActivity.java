package com.example.coen391app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private SeekBar phSeekBar;
    private Switch autoWateringSwitch, uvLightSwitch;
    private ProgressBar healthBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure this matches your XML file

        // Initialize UI components
        phSeekBar = findViewById(R.id.ph_seekbar);
        autoWateringSwitch = findViewById(R.id.auto_watering_switch);
        uvLightSwitch = findViewById(R.id.uv_light_switch);
        healthBar = findViewById(R.id.health_bar);

        // Set any necessary listeners or default values for the UI components
        phSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Handle pH value change
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
