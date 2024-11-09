package com.example.coen391app.Objects;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coen391app.R;

public class PlantViewHolder extends RecyclerView.ViewHolder {
    TextView plantNameTextView, plantTypeTextView;

    public PlantViewHolder(@NonNull View itemView){
        super(itemView);
        plantNameTextView = itemView.findViewById(R.id.plantNameTextView);
        plantTypeTextView = itemView.findViewById(R.id.plantTypeTextView);
    }
}
