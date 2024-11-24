package com.example.coen391app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.coen391app.Objects.Plant;
import com.example.coen391app.Objects.PlantTemplate;
import com.example.coen391app.Objects.PlantTemplateProvider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddPlantActivity extends AppCompatActivity {

    private EditText inputNickname, inputDate;
    private Spinner spinner;
    private TextView plantDescription;
    private Button addPlantBtn;
    private List<String> plantTemplates = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        databaseHelper = DatabaseHelper.getInstance("plants");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Plant");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputNickname = findViewById(R.id.input_nickname);
        inputDate = findViewById(R.id.input_date);
        spinner = findViewById(R.id.spinner_plant_type);
        plantDescription = findViewById(R.id.plant_description);
        addPlantBtn = findViewById(R.id.btn_add_plant);


        for(PlantTemplate template : PlantTemplateProvider.getPlantTmeplates()) {
            plantTemplates.add(template.getCommonName());
        }


        // Populating the spinner with the names of the plants
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                plantTemplates
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Update Description of selected plant
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPlant = plantTemplates.get(position);
                String description = PlantTemplateProvider.getPlantTmeplates().get(position).getDescription();
                plantDescription.setText(description);
                plantDescription.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                plantDescription.setText("");
                plantDescription.setVisibility(View.INVISIBLE);

            }
        });

        // Handling the add plant button click
        addPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Constructing the plant object to be added to db
                String plantNickName = inputNickname.getText().toString();
                int templateIndex = plantTemplates.indexOf(spinner.getSelectedItem().toString());
                //add plantAge logic here
                PlantTemplate plantTemplate = PlantTemplateProvider.getPlantTmeplates().get(templateIndex);
                Plant plant = new Plant(plantNickName, plantTemplate);

                if(plantNickName.isEmpty()){
                    Toast.makeText(AddPlantActivity.this, "Nickname is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                //add plant to database
                databaseHelper.addPlant(plant, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

    }
}
