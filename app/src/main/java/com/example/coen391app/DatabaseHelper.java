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

    public void addPlant(Plant plant, OnSuccessListener<Void> successListener, OnFailureListener failureListener){
        reference.push().setValue(plant)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    public DatabaseReference getReference() {
        return reference;
    }

    //ADD METHOD TO CHECK IF AN ELEMENT EXISTS IN THE DB BY ITS NAME



}
