package com.example.coen391app;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.coen391app.Objects.Plant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;

public class DatabaseHelper {
    private static DatabaseHelper instance;
    private final DatabaseReference reference;

    private DatabaseHelper(String ref){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        this.reference = db.getReference(ref);
    }

    public static synchronized DatabaseHelper getInstance(String ref){
        if(instance == null){
            instance = new DatabaseHelper(ref);
        }
        return instance;
    }

    //add plant method
    public void addPlant(Plant plant, OnSuccessListener<Void> successListener, OnFailureListener failureListener){
        reference.push().setValue(plant)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    //Retrieve all plants
    public void getAllPlants(ValueEventListener listener){
        reference.addValueEventListener(listener);
    }

    public void getPlantByNickName(String nickname, final ValueEventListener listener) {
        reference.orderByChild("nickname").equalTo(nickname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot plantSnapshot : snapshot.getChildren()){
                        Plant plant = plantSnapshot.getValue(Plant.class);
                        listener.onDataChange(snapshot);
                    }
                }
                else{
                    listener.onCancelled(DatabaseError.fromException(new Exception("Plant not found"))) ;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onCancelled(error);
            }
        });
    }

    // method to retrieve sensor data
    public void getSensorData(final SensorDataValueListener listener){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Double temperature = snapshot.child("temperature").getValue(Double.class);
                    Double moisturePercent = snapshot.child("moisturePercent").getValue(Double.class);
                    Double light = snapshot.child("light").getValue(Double.class);

                    if(temperature != null && moisturePercent != null && light != null) {
                        listener.onValuesRetrieved(temperature, moisturePercent, light);
                    }
                    else {
                        listener.onCancelled(DatabaseError.fromException(new Exception("Missing some values")));
                    }
                }else{
                    listener.onCancelled(DatabaseError.fromException(new Exception("Sensor data not found")));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onCancelled(error);
            }
        });
    }



    public DatabaseReference getReference() {
        return reference;
    }

    public interface SensorDataValueListener{
        void onValuesRetrieved(double temperature, double moisturePercent, double light);
        void onCancelled(DatabaseError error);
    }

    //ADD METHOD TO CHECK IF AN ELEMENT EXISTS IN THE DB BY ITS NAME

}
