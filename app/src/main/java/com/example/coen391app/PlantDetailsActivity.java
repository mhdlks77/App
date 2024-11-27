package com.example.coen391app;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.coen391app.Objects.Plant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlantDetailsActivity extends AppCompatActivity {

    private Plant plant;
    private String nickname;
    private double temp, moisture, lightLevel, tempRecommended, moistureRecommended, uvRecommended;
    private boolean btnEnabled;
    private TextView plantTemperatureView, plantMoistureLevelView, plantUVLightView;
    private Button setDefaultButton;
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


        // Initialize TextViews
        TextView plantNicknameView = findViewById(R.id.plantNicknameTextView);
        TextView plantAgeView = findViewById(R.id.plantAgeTextView);
        TextView plantCommonNameView = findViewById(R.id.plantCommonNameTextView);
        TextView plantScientificNameView = findViewById(R.id.plantScientificNameTextView);
        TextView plantDescriptionView = findViewById(R.id.plantDescriptionTextView);
        TextView plantRecommendedTempView = findViewById(R.id.plantRecommendedTempTextView);
        TextView plantRecommendedMoistureView = findViewById(R.id.plantRecommendedMoistureTextView);
        plantTemperatureView = findViewById(R.id.plantTemperatureTextView);
        plantUVLightView = findViewById(R.id.plantUVLightTextView);
        plantMoistureLevelView = findViewById(R.id.plantMoistureLevelTextView);
        TextView plantRecommendedUVView = findViewById(R.id.plantRecommendedUVTextView);
        setDefaultButton = findViewById(R.id.set_as_default_button);

        // Retrieve data from Intent
        String nickname = getIntent().getStringExtra("plantNickname");
        String commonName = getIntent().getStringExtra("plantCommonName");
        String scientificName = getIntent().getStringExtra("plantScientificName");
        String description = getIntent().getStringExtra("plantDescription");
        int age = getIntent().getIntExtra("plantAge", 0);


        tempRecommended = getIntent().getDoubleExtra("plantRecommendedTemperature", 0.0);
        moistureRecommended = getIntent().getDoubleExtra("plantRecommendedSoilMoistureLevel", 0.0);
        uvRecommended = getIntent().getDoubleExtra("plantRecommendedUvLight", 0.0);
        boolean defaultPlant = getIntent().getBooleanExtra("defaultPlant", false);


        // Set data to views
        plantNicknameView.setText(nickname);
        plantAgeView.setText(age + " days old");
        plantCommonNameView.setText(commonName);
        plantScientificNameView.setText(scientificName);
        plantDescriptionView.setText(description);
        plantRecommendedTempView.setText("(Recommended: " + tempRecommended + ")");
        plantRecommendedMoistureView.setText("(Recommended: " + moistureRecommended + ")");
        plantRecommendedUVView.setText("(Recommended: " + uvRecommended + ")");

        //////////
        getSensorDataFromDatabase();
        //////////


        //default button logic
        setDefaultButton.setOnClickListener(v -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            //setting default attribute for all plants to false except for current
            database.getReference().child("plants").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot plantSnapshot : snapshot.getChildren()){
                        String currentNickname = plantSnapshot.child("nickname").getValue(String.class);
                        if(currentNickname != null && currentNickname.equals(nickname)){
                            plantSnapshot.getRef().child("default_plant").setValue(true);
                            database.getReference().child("Automation").child("Ideal_Moisture").setValue(moistureRecommended);
                            //show toast
                            runOnUiThread(()->{
                                Toast.makeText(PlantDetailsActivity.this, "Default plant selected successfully", Toast.LENGTH_SHORT).show();
                            });
                            setDefaultButton.setText("This is the Default Plant");
                            setDefaultButton.setEnabled(false);
                            break;
                        }
                        else {
                            plantSnapshot.getRef().child("default").setValue(false);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("PlantDetailsActivity", "Error updating default plant " + error.getMessage());
                }
            });
        });
    }

    private void getSensorDataFromDatabase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("SensorData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Double temperature = snapshot.child("temperature").getValue(Double.class);
                    Double moisturePercent = snapshot.child("moisturePercent").getValue(Double.class);
                    Double light = snapshot.child("light").getValue(Double.class);

                    if(Math.abs(temperature - tempRecommended) > tempRecommended * 0.1 ){
                        sendNotification("Temperature alert", "Temperature is out of the recommended range!");
                    }

                    if(Math.abs(moisturePercent - moistureRecommended) > moistureRecommended * 0.1 ){
                        sendNotification("Soil Moisture alert", "Soil Moisture is out of the recommended range!");
                    }

                    if(Math.abs(light - uvRecommended) > uvRecommended * 0.1 ){
                        sendNotification("Lighting alert", "Lighting is out of the recommended range!");
                    }

                    if (temperature != null && moisturePercent != null && light != null) {
                        plantTemperatureView.setText("Temperature: " + temperature + "°C");
                        plantMoistureLevelView.setText("Moisture Level: " + moisturePercent + "%");
                        plantUVLightView.setText("UV Light: " + light + " units");


                    } else {
                        Log.e("PlantDetailsActivity", "One or more sensor values are null or missing.");
                    }

                } else {
                    Log.e("PlantDetailsActivity", "SensorData node not found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("PlantDetailsActivity", "Error retrieving data: " + error.getMessage());
            }

        });

        database.getReference().child("plants").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot plantSnapshot : snapshot.getChildren()){
                    String currentNickname = plantSnapshot.child("nickname").getValue(String.class);
                    if(currentNickname != null && currentNickname.equals(nickname)){
                        btnEnabled = plantSnapshot.child("default_plant").getValue(Boolean.class);
                        break;
                    }
                    else {
                        continue;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("PlantDetailsActivity", "Error retrieving default status " + error.getMessage());
            }
        });

    }

    @SuppressLint("MissingPermission")
    private void sendNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.ic_notification) // Your custom icon
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
    }



}
