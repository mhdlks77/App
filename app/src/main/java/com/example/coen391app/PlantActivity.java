package com.example.coen391app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coen391app.Objects.Plant;
import com.example.coen391app.Objects.PlantAdapter;

import java.util.ArrayList;

public class PlantActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Plant> plants = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plants);

        // -------------Toolbar------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My plants");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-------------RecyclerView for plants list---------
        recyclerView = findViewById(R.id.plantsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        plants.add(new Plant("Plant 1", "Aloe Vera"));
        plants.add(new Plant("Plant 2", "Fiddle Leaf Fig"));

        PlantAdapter plantAdapter = new PlantAdapter(plants);
        recyclerView.setAdapter(plantAdapter);

    }


}
