package com.example.coen391app;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataActivity extends AppCompatActivity {

    private DatabaseReference rootDatabaseRef;
    private TextView dataTextView;
    private TextView lightValue, moistureValue, temperatureValue; // Percentages
    private ProgressBar lightProgressBar, moistureProgressBar, temperatureProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // Initialize views
       dataTextView = findViewById(R.id.dataTextView);
       lightProgressBar = findViewById(R.id.lightProgressBar);
       moistureProgressBar = findViewById(R.id.moistureProgressBar);
       temperatureProgressBar = findViewById(R.id.temperatureProgressBar);
       lightValue = findViewById(R.id.lightValue);
       moistureValue = findViewById(R.id.moistureValue);
       temperatureValue = findViewById(R.id.temperatureValue);

        //Setup Toolbar with back navigation
        Toolbar settings_toolbar = findViewById(R.id.data_toolbar);
        setSupportActionBar(settings_toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize Firebase Database reference
        rootDatabaseRef = FirebaseDatabase.getInstance().getReference();

        // Retrieve data from Firebase
        retrieveData();
    }


    private void retrieveData() {
        rootDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder data = new StringBuilder();

                // Watering data
                Boolean lightingStatus = dataSnapshot.child("Automation").child("Lighting").getValue(Boolean.class);
                if (lightingStatus != null) {
                    data.append("Lighting: ").append(lightingStatus).append("\n");
                } else {
                    data.append("Lighting: Not available\n");
                }


                Boolean wateringStatus = dataSnapshot.child("Automation").child("Watering").getValue(Boolean.class);
                if (wateringStatus != null) {
                    data.append("Watering: ").append(wateringStatus).append("\n");
                } else {
                    data.append("Watering: Not available\n");
                }

                // Ideal Moisture
                Integer idealMoisture = dataSnapshot.child("Automation").child("Ideal_Moisture").getValue(Integer.class);
                if (idealMoisture == null) {
                    idealMoisture = 50; // Default value if not available (you can adjust this as needed)
                }
                data.append("Ideal Moisture: ").append(idealMoisture).append("%\n"); // Show Ideal Moisture value

                // Sensor Data
                DataSnapshot sensorData = dataSnapshot.child("SensorData");
                if (sensorData.exists()) {
                    Integer light = sensorData.child("light").getValue(Integer.class);
                    Integer moisturePercent = sensorData.child("moisturePercent").getValue(Integer.class);
                    Double temperature = sensorData.child("temperature").getValue(Double.class);

                    data.append("Sensor Data:\n");
                    data.append("Light: ").append(light != null ? light : "Not available").append("\n");
                    data.append("Moisture Percent: ").append(moisturePercent != null ? moisturePercent : "Not available").append("\n");

                    // Show temperature in data
                    data.append("Temperature: ").append(temperature != null ? temperature : "Not available").append("\n");

                    // Update ProgressBars and Percentages
                    if (light != null) {
                        lightProgressBar.setProgress(light);
                        lightValue.setText(light + " lux");
                    }

                    if (moisturePercent != null) {
                        moistureProgressBar.setProgress(moisturePercent); // Show actual progress
                        moistureValue.setText(moisturePercent + "%");

                        // Turn the progress bar red if moisture is below 20% of the ideal moisture or above 25% of the ideal moisture
                        if (moisturePercent < (idealMoisture - 20) || moisturePercent > (idealMoisture + 25)) {
                            moistureProgressBar.getProgressDrawable().setColorFilter(
                                    ContextCompat.getColor(DataActivity.this, android.R.color.holo_red_light),
                                    android.graphics.PorterDuff.Mode.SRC_IN
                            );
                        } else {
                            // Optional: Reset to default (if you want to remove any color change when within acceptable range)
                            moistureProgressBar.getProgressDrawable().clearColorFilter();
                        }
                    }




                    if (temperature != null) {
                        temperatureProgressBar.setProgress(temperature.intValue());
                        temperatureValue.setText(temperature.intValue() + "°C");
                    }
                } else {
                    data.append("Sensor Data: Not available\n");
                }

                // Log and display the retrieved data
                Log.d("DataActivity", "Data Retrieved: " + data.toString());
                dataTextView.setText(data.toString()); // Display the full data, including ideal moisture and temperature
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DataActivity", "Failed to read value.", databaseError.toException());
            }
        });
    }

}

