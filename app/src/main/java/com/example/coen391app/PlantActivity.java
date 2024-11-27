package com.example.coen391app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coen391app.Objects.Plant;
import com.example.coen391app.Objects.PlantAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlantActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Plant> plants = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private PlantAdapter plantAdapter;

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

        //Initializing databaseHelper
        databaseHelper = DatabaseHelper.getInstance("plants");


        plantAdapter = new PlantAdapter(plants);
        recyclerView.setAdapter(plantAdapter);

        //fetch plants from database method call
        fetchPlantsFromDatabase();

        //edit plant access logic

    }



    private void fetchPlantsFromDatabase() {
        databaseHelper.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plants.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Plant plant = snapshot1.getValue(Plant.class);
                    if(plant != null){
                        plants.add(plant);
                    }
                }
                plantAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("PlantActivity", "Failed to load plants", error.toException());
            }
        });
    }


}
