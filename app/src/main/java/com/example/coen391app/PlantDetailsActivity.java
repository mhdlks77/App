package com.example.coen391app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PlantDetailsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plant Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView plantNameView = findViewById(R.id.plantNameTextView);
        TextView plantTypeView = findViewById(R.id.plantTypeTextView);
        TextView plantAgeView = findViewById(R.id.plantAgeTextView);
        TextView plantTemperatureView = findViewById(R.id.plantTemperatureTextView);
        TextView plantUVLightView = findViewById(R.id.plantUVLightTextView);
        TextView plantSoilView = findViewById(R.id.plantSoilTextView);

        //--------------Retrieving data from intent------------
        String plantName = getIntent().getStringExtra("plantName");
        String plantType = getIntent().getStringExtra("plantType");
        int plantAge = getIntent().getIntExtra("plantAge",0);
        double plantTemperature = getIntent().getDoubleExtra("plantTemperature", 0.0);
        double plantUVLight = getIntent().getDoubleExtra("plantUVLight", 0.0);
        double plantSoil = getIntent().getDoubleExtra("plantSoil", 0.0);

        //------------setting plant details-------------
        plantNameView.setText(plantName);
        plantTypeView.setText(plantType);
        plantAgeView.setText("This plant is " + plantAge + " days old");
        plantTemperatureView.setText("Temperature: " + plantTemperature + " °C");
        plantUVLightView.setText("UV Lighting level " + plantUVLight + " units");
        plantSoilView.setText("Soil Moisture Level: " + plantSoil + "%");



    }
}
