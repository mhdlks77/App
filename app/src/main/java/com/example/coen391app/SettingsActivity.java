package com.example.coen391app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference rootDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button wifiSetup_btn = findViewById(R.id.wifi_setup_button);
        SwitchCompat wateringSwitch = findViewById(R.id.wateringSwitch);

        //Setup Toolbar with back navigation
        Toolbar settings_toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(settings_toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Initialize Firebase Database reference
        rootDatabaseRef = FirebaseDatabase.getInstance().getReference();

        //Watering Switch
        wateringSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                rootDatabaseRef.child("Automation").child("Watering").setValue(isChecked)
                .addOnSuccessListener(aVoid -> Log.d("DataActivity", "Watering updated to " + isChecked))
                .addOnFailureListener(e -> Log.w("DataActivity", "Failed to update Watering", e))
        );


        //Wifi Credentials Setup
        wifiSetup_btn.setOnClickListener(v -> {
            // Check for ACCESS_FINE_LOCATION permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                Toast.makeText(this, "Please press again after accepting permissions!", Toast.LENGTH_SHORT).show();

            }else {
                // If permission is granted, show the DialogFragment
                DialogFragment dialogFragment = new WifiSetup();
                dialogFragment.show(getSupportFragmentManager(), "Wifi Setup");
            }

        });

    }
}