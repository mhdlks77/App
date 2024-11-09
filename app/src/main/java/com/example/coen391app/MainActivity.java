package com.example.coen391app;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
