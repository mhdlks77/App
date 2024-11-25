package com.example.coen391app;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlantDetailsActivity extends AppCompatActivity {
    private DatabaseReference plantRef;
    private TextView plantNameView, plantAgeView, plantTemperatureView, plantUVLightView, plantSoilView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        // Initialize views
        Toolbar toolbar = findViewById(R.id.toolbar);
        plantNameView = findViewById(R.id.plantNameTextView);
        plantAgeView = findViewById(R.id.plantAgeTextView);
        plantTemperatureView = findViewById(R.id.plantTemperatureTextView);
        plantUVLightView = findViewById(R.id.plantUVLightTextView);
        plantSoilView = findViewById(R.id.plantSoilTextView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plant Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve plant ID from the intent
        String plantId = getIntent().getStringExtra("plantId");
        if (plantId == null) {
            Toast.makeText(this, "No plant selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Firebase reference for the specific plant
        plantRef = FirebaseDatabase.getInstance().getReference("plants").child(plantId);

        // Fetch and display plant details
        loadPlantDetails();
    }

    private void loadPlantDetails() {
        plantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Parse the plant data
                    String plantName = snapshot.child("nickname").getValue(String.class);
                    int plantAge = snapshot.child("age").getValue(Integer.class) != null ?
                            snapshot.child("age").getValue(Integer.class) : 0;
                    double plantTemperature = snapshot.child("temperature").getValue(Double.class) != null ?
                            snapshot.child("temperature").getValue(Double.class) : 0.0;
                    double plantUVLight = snapshot.child("uvLighting").getValue(Double.class) != null ?
                            snapshot.child("uvLighting").getValue(Double.class) : 0.0;
                    double plantSoil = snapshot.child("soilMoistureLevel").getValue(Double.class) != null ?
                            snapshot.child("soilMoistureLevel").getValue(Double.class) : 0.0;

                    // Set data to views
                    plantNameView.setText(plantName);
                    plantAgeView.setText("This plant is " + plantAge + " days old");
                    plantTemperatureView.setText("Temperature: " + plantTemperature + " °C");
                    plantUVLightView.setText("UV Lighting level " + plantUVLight + " units");
                    plantSoilView.setText("Soil Moisture Level: " + plantSoil + "%");
                } else {
                    Toast.makeText(PlantDetailsActivity.this, "Plant details not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PlantDetailsActivity.this, "Failed to load plant details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
