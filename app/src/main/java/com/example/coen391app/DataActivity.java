package com.example.coen391app;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data); // Set your layout file here

        dataTextView = findViewById(R.id.dataTextView); // Reference your TextView

        // Initialize Firebase Database reference
        rootDatabaseRef = FirebaseDatabase.getInstance().getReference(); // Point to root

        // Retrieve data from Firebase
        retrieveData();
    }

    private void retrieveData() {
        rootDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder data = new StringBuilder();

                // Get Lighting data
                Boolean lightingStatus = dataSnapshot.child("Automation").child("Lighting").getValue(Boolean.class);
                data.append("Lighting: ").append(lightingStatus).append("\n");

                // Get Watering data
                Boolean wateringStatus = dataSnapshot.child("Automation").child("Watering").getValue(Boolean.class);
                data.append("Watering: ").append(wateringStatus).append("\n");

                // Get SensorData
                DataSnapshot sensorData = dataSnapshot.child("SensorData");
                if (sensorData.exists()) {
                    int uvLevel = sensorData.child("UVLevel").getValue(Integer.class);
                    int moisturePercent = sensorData.child("moisturePercent").getValue(Integer.class);
                    int temperature = sensorData.child("temperature").getValue(Integer.class);

                    data.append("Sensor Data:\n");
                    data.append("UV Level: ").append(uvLevel).append("\n");
                    data.append("Moisture Percent: ").append(moisturePercent).append("\n");
                    data.append("Temperature: ").append(temperature).append("\n");
                }

                dataTextView.setText(data.toString()); // Set the data to the TextView
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DataActivity", "Failed to read value.", databaseError.toException());
            }
        });
    }

}
