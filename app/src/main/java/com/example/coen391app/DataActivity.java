package com.example.coen391app;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataActivity extends AppCompatActivity {

    private DatabaseReference rootDatabaseRef;
    private TextView dataTextView; // TextView to display data
    private Switch lightingSwitch, wateringSwitch; // Switch components
    private ProgressBar lightProgressBar, moistureProgressBar, temperatureProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data); // Set your layout file here

        dataTextView = findViewById(R.id.dataTextView); // Reference your TextView
        lightingSwitch = findViewById(R.id.lightingSwitch); // Reference your Lighting Switch
        wateringSwitch = findViewById(R.id.wateringSwitch); // Reference your Watering Switch
        lightProgressBar = findViewById(R.id.lightProgressBar);
        moistureProgressBar = findViewById(R.id.moistureProgressBar);
        temperatureProgressBar = findViewById(R.id.temperatureProgressBar);
        // Initialize Firebase Database reference
        rootDatabaseRef = FirebaseDatabase.getInstance().getReference(); // Point to root

        // Set up listeners for the switches
        setupSwitchListeners();

        // Retrieve data from Firebase
        retrieveData();
    }

    private void setupSwitchListeners() {
        lightingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update Firebase when the Lighting switch is toggled
            rootDatabaseRef.child("Automation").child("Lighting").setValue(isChecked)
                    .addOnSuccessListener(aVoid -> Log.d("DataActivity", "Lighting updated to " + isChecked))
                    .addOnFailureListener(e -> Log.w("DataActivity", "Failed to update Lighting", e));
        });

        wateringSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update Firebase when the Watering switch is toggled
            rootDatabaseRef.child("Automation").child("Watering").setValue(isChecked)
                    .addOnSuccessListener(aVoid -> Log.d("DataActivity", "Watering updated to " + isChecked))
                    .addOnFailureListener(e -> Log.w("DataActivity", "Failed to update Watering", e));
        });
    }

    private void retrieveData() {
        rootDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder data = new StringBuilder();

                // Get Lighting data with null check
                Boolean lightingStatus = dataSnapshot.child("Automation").child("Lighting").getValue(Boolean.class);
                if (lightingStatus != null) {
                    lightingSwitch.setChecked(lightingStatus); // Sync switch with Firebase value
                    data.append("Lighting: ").append(lightingStatus).append("\n");
                } else {
                    data.append("Lighting: Not available\n");
                }

                // Get Watering data with null check
                Boolean wateringStatus = dataSnapshot.child("Automation").child("Watering").getValue(Boolean.class);
                if (wateringStatus != null) {
                    wateringSwitch.setChecked(wateringStatus); // Sync switch with Firebase value
                    data.append("Watering: ").append(wateringStatus).append("\n");
                } else {
                    data.append("Watering: Not available\n");
                }

                Integer idealMoisture = dataSnapshot.child("Automation").child("Ideal Moisture").getValue(Integer.class);
                if (idealMoisture != null) {
                    data.append("Ideal Moisture: ").append(idealMoisture).append("\n");
                } else {
                    data.append("Ideal Moisture: Not available\n");
                }

                // Get SensorData with null checks for each field
                DataSnapshot sensorData = dataSnapshot.child("SensorData");
                if (sensorData.exists()) {
                    Integer light = sensorData.child("light").getValue(Integer.class);
                    Integer moisturePercent = sensorData.child("moisturePercent").getValue(Integer.class);
                    Double temperature = sensorData.child("temperature").getValue(Double.class);

                    data.append("Sensor Data:\n");
                    data.append("Light: ").append(light != null ? light : "Not available").append("\n");
                    data.append("Moisture Percent: ").append(moisturePercent != null ? moisturePercent : "Not available").append("\n");
                    data.append("Temperature: ").append(temperature != null ? temperature : "Not available").append("\n");

                    // Update ProgressBars
                    if (light != null) {
                        lightProgressBar.setProgress(light); // Update light progress
                    }

                    if (moisturePercent != null) {
                        moistureProgressBar.setProgress(moisturePercent); // Update moisture progress
                    }

                    if (temperature != null) {
                        temperatureProgressBar.setProgress(temperature.intValue()); // Update temperature progress
                    }
                } else {
                    data.append("Sensor Data: Not available\n");
                }

                // Log the data retrieved
                Log.d("DataActivity", "Data Retrieved: " + data.toString());

                // Set the data to the TextView
                dataTextView.setText(data.toString()); // Set the data to the TextView
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DataActivity", "Failed to read value.", databaseError.toException());
            }
        });
    }
}

