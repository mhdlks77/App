package com.example.coen391app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.coen391app.Objects.Plant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class PlantDetailsActivity extends AppCompatActivity {

    private Plant plant;
    private String nickname;
    private DatabaseHelper database;
    private DatabaseHelper database2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plant Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = DatabaseHelper.getInstance("plants");
        database2 = DatabaseHelper.getInstance("SensorData");

        // Initialize TextViews
        TextView plantNicknameView = findViewById(R.id.plantNicknameTextView);
        TextView plantAgeView = findViewById(R.id.plantAgeTextView);
        TextView plantCommonNameView = findViewById(R.id.plantCommonNameTextView);
        TextView plantScientificNameView = findViewById(R.id.plantScientificNameTextView);
        TextView plantDescriptionView = findViewById(R.id.plantDescriptionTextView);
        TextView plantTemperatureView = findViewById(R.id.plantTemperatureTextView);
        TextView plantRecommendedTempView = findViewById(R.id.plantRecommendedTempTextView);
        TextView plantMoistureLevelView = findViewById(R.id.plantMoistureLevelTextView);
        TextView plantRecommendedMoistureView = findViewById(R.id.plantRecommendedMoistureTextView);
        TextView plantUVLightView = findViewById(R.id.plantUVLightTextView);
        TextView plantRecommendedUVView = findViewById(R.id.plantRecommendedUVTextView);
        Button setDefaultButton = findViewById(R.id.set_as_default_button);

        // Retrieve data from Intent
        String nickname = getIntent().getStringExtra("plantNickname");
        String commonName = getIntent().getStringExtra("plantCommonName");
        String scientificName = getIntent().getStringExtra("plantScientificName");
        String description = getIntent().getStringExtra("plantDescription");
        int age = getIntent().getIntExtra("plantAge", 0);

        //temp
        double temp = getIntent().getDoubleExtra("plantTemperature", 0.0);
        double tempRecommended = getIntent().getDoubleExtra("plantRecommendedTemperature", 0.0);

        //soil moisture
        double moisture = getIntent().getDoubleExtra("plantSoilMoistureLevel", 0.0);
        double moistureRecommended = getIntent().getDoubleExtra("plantRecommendedSoilMoistureLevel", 0.0);

        //light
        double uv = getIntent().getDoubleExtra("plantUvLight", 0.0);
        double uvRecommended = getIntent().getDoubleExtra("plantRecommendedUvLight", 0.0);

        // Set data to views
        plantNicknameView.setText(nickname);
        plantAgeView.setText(age + " days old");
        plantCommonNameView.setText(commonName);
        plantScientificNameView.setText(scientificName);
        plantDescriptionView.setText(description);
        plantTemperatureView.setText("Temperature: " + temp + "°C");
        plantRecommendedTempView.setText("(Recommended: " + tempRecommended + ")");
        plantMoistureLevelView.setText("Moisture Level: " + moisture + "%");
        plantRecommendedMoistureView.setText("(Recommended: " + moistureRecommended + ")");
        plantUVLightView.setText("UV Lighting: " + uv + " units");
        plantRecommendedUVView.setText("(Recommended: " + uvRecommended + ")");

        setDefaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
