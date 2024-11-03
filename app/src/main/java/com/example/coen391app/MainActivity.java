package com.example.coen391app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up click listeners for each block
        findViewById(R.id.block1).setOnClickListener(view -> openActivity(PlantActivity.class));
        findViewById(R.id.block2).setOnClickListener(view -> openActivity(AddPlantActivity.class));
        findViewById(R.id.block3).setOnClickListener(view -> openActivity(DataActivity.class));
        findViewById(R.id.block4).setOnClickListener(view -> openActivity(SettingsActivity.class));
    }

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
